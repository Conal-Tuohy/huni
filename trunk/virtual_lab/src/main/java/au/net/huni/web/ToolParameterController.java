package au.net.huni.web;

import au.net.huni.model.HistoryItem;
import au.net.huni.model.ToolParameter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
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
@RooWebScaffold(path = "toolparameters", formBackingObject = ToolParameter.class)
@RooWebJson(jsonObject = ToolParameter.class)
public class ToolParameterController {

    @RequestMapping(value = "/console/toolparameters", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid ToolParameter toolParameter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolParameter);
            return "toolparameters/create";
        }
        uiModel.asMap().clear();
        toolParameter.persist();
        return "redirect:/console/toolparameters/" + encodeUrlPathSegment(toolParameter.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/toolparameters/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ToolParameter toolParameter = ToolParameter.findToolParameter(id);
        toolParameter.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/toolparameters";
    }

    @RequestMapping(value = "/console/toolparameters", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid ToolParameter toolParameter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolParameter);
            return "toolparameters/update";
        }
        uiModel.asMap().clear();
        toolParameter.merge();
        return "redirect:/console/toolparameters/" + encodeUrlPathSegment(toolParameter.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/toolparameters", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new ToolParameter());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (HistoryItem.countHistoryItems() == 0) {
            dependencies.add(new String[] { "historyitem", "historyitems" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "toolparameters/create";
    }

	@RequestMapping(value = "/console/toolparameters/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("toolparameter", ToolParameter.findToolParameter(id));
        uiModel.addAttribute("itemId", id);
        return "toolparameters/show";
    }

	@RequestMapping(value = "/console/toolparameters", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("toolparameters", ToolParameter.findToolParameterEntries(firstResult, sizeNo));
            float nrOfPages = (float) ToolParameter.countToolParameters() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("toolparameters", ToolParameter.findAllToolParameters());
        }
        return "toolparameters/list";
    }

	@RequestMapping(value = "/console/toolparameters/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, ToolParameter.findToolParameter(id));
        return "toolparameters/update";
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

	void populateEditForm(Model uiModel, ToolParameter toolParameter) {
        uiModel.addAttribute("toolParameter", toolParameter);
        uiModel.addAttribute("historyitems", HistoryItem.findAllHistoryItems());
    }

	@RequestMapping(value = "/rest/toolparameters", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        ToolParameter toolParameter = ToolParameter.fromJsonToToolParameter(json);
        toolParameter.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/toolparameters/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (ToolParameter toolParameter: ToolParameter.fromJsonArrayToToolParameters(json)) {
            toolParameter.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/toolparameters/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        ToolParameter toolParameter = ToolParameter.findToolParameter(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (toolParameter == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        toolParameter.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/toolparameters", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<ToolParameter> result = ToolParameter.findAllToolParameters();
        return new ResponseEntity<String>(ToolParameter.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/toolparameters/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        ToolParameter toolParameter = ToolParameter.findToolParameter(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (toolParameter == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(toolParameter.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/toolparameters", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        ToolParameter toolParameter = ToolParameter.fromJsonToToolParameter(json);
        if (toolParameter.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/toolparameters/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (ToolParameter toolParameter: ToolParameter.fromJsonArrayToToolParameters(json)) {
            if (toolParameter.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
