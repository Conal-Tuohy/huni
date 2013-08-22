package au.net.huni.web;

import au.net.huni.model.HistoryItem;
import au.net.huni.model.Researcher;
import au.net.huni.model.ToolParameter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

//Access by default is restricted to the ADMIN role within the console webapp.
//This is over-ridden by annotations in this file.
//See webmvc-config.xml
@Controller
@RooWebScaffold(path = "historyitems", formBackingObject = HistoryItem.class)
@RooWebJson(jsonObject = HistoryItem.class)
public class HistoryItemController {

    @RequestMapping(value = "/console/historyitems", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid HistoryItem historyItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, historyItem);
            return "historyitems/create";
        }
        uiModel.asMap().clear();
        historyItem.persist();
        return "redirect:/console/historyitems/" + encodeUrlPathSegment(historyItem.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/historyitems/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        HistoryItem historyItem = HistoryItem.findHistoryItem(id);
        historyItem.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/historyitems";
    }

    @RequestMapping(value = "/console/historyitems", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid HistoryItem historyItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, historyItem);
            return "historyitems/update";
        }
        uiModel.asMap().clear();
        Researcher owner = historyItem.getOwner().merge();
        historyItem.merge();
        historyItem.setOwner(owner);
        return "redirect:/console/historyitems/" + encodeUrlPathSegment(historyItem.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/historyitems", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new HistoryItem());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (Researcher.countResearchers() == 0) {
            dependencies.add(new String[] { "researcher", "researchers" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "historyitems/create";
    }

	@RequestMapping(value = "/console/historyitems/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("historyitem", HistoryItem.findHistoryItem(id));
        uiModel.addAttribute("itemId", id);
        return "historyitems/show";
    }

	@RequestMapping(value = "/console/historyitems", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("historyitems", HistoryItem.findHistoryItemEntries(firstResult, sizeNo));
            float nrOfPages = (float) HistoryItem.countHistoryItems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("historyitems", HistoryItem.findAllHistoryItems());
        }
        addDateTimeFormatPatterns(uiModel);
        return "historyitems/list";
    }

	@RequestMapping(value = "/console/historyitems/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, HistoryItem.findHistoryItem(id));
        return "historyitems/update";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("historyItem_executiondate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, HistoryItem historyItem) {
        uiModel.addAttribute("historyItem", historyItem);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("researchers", Researcher.findAllResearchers());
        uiModel.addAttribute("toolparameters", ToolParameter.findAllToolParameters());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/rest/historyitems", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        HistoryItem historyItem = HistoryItem.fromJsonToHistoryItem(json);
        historyItem.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/rest/historyitems/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (HistoryItem historyItem: HistoryItem.fromJsonArrayToHistoryItems(json)) {
            historyItem.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/rest/historyitems/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        HistoryItem historyItem = HistoryItem.findHistoryItem(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (historyItem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        historyItem.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/rest/historyitems", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<HistoryItem> result = HistoryItem.findAllHistoryItems();
        return new ResponseEntity<String>(HistoryItem.toJsonArray(result), headers, HttpStatus.OK);
    }

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/rest/historyitems/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        HistoryItem historyItem = HistoryItem.findHistoryItem(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (historyItem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(historyItem.toJson(), headers, HttpStatus.OK);
    }

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/rest/historyitems", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HistoryItem historyItem = HistoryItem.fromJsonToHistoryItem(json);
        if (historyItem.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/rest/historyitems/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (HistoryItem historyItem: HistoryItem.fromJsonArrayToHistoryItems(json)) {
            if (historyItem.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
