package au.net.huni.web;

import au.net.huni.model.FeedbackItem;
import au.net.huni.model.Rating;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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

@Controller
@RooWebScaffold(path = "feedbackitems", formBackingObject = FeedbackItem.class)
@RooWebJson(jsonObject = FeedbackItem.class)
public class FeedbackItemController {

    @RequestMapping(value = "/console/feedbackitems", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid FeedbackItem feedbackItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, feedbackItem);
            return "feedbackitems/create";
        }
        uiModel.asMap().clear();
        feedbackItem.persist();
        return "redirect:/console/feedbackitems/" + encodeUrlPathSegment(feedbackItem.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/feedbackitems/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        FeedbackItem feedbackItem = FeedbackItem.findFeedbackItem(id);
        feedbackItem.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/feedbackitems";
    }

    @RequestMapping(value = "/console/feedbackitems", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid FeedbackItem feedbackItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, feedbackItem);
            return "feedbackitems/update";
        }
        uiModel.asMap().clear();
        feedbackItem.merge();
        return "redirect:/console/feedbackitems/" + encodeUrlPathSegment(feedbackItem.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/feedbackitems", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new FeedbackItem());
        return "feedbackitems/create";
    }

    @RequestMapping(value = "/console/feedbackitems/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("feedbackitem", FeedbackItem.findFeedbackItem(id));
        uiModel.addAttribute("itemId", id);
        return "feedbackitems/show";
    }

    @RequestMapping(value = "/console/feedbackitems", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("feedbackitems", FeedbackItem.findFeedbackItemEntries(firstResult, sizeNo));
            float nrOfPages = (float) FeedbackItem.countFeedbackItems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("feedbackitems", FeedbackItem.findAllFeedbackItems());
        }
        return "feedbackitems/list";
    }

    @RequestMapping(value = "/console/feedbackitems//{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, FeedbackItem.findFeedbackItem(id));
        return "feedbackitems/update";
    }

    void populateEditForm(Model uiModel, FeedbackItem feedbackItem) {
        uiModel.addAttribute("feedbackItem", feedbackItem);
        uiModel.addAttribute("ratings", Arrays.asList(Rating.values()));
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }

	@RequestMapping(value = "/rest/feedbackitems", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        FeedbackItem feedbackItem = FeedbackItem.fromJsonToFeedbackItem(json);
        feedbackItem.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/feedbackitems/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (FeedbackItem feedbackItem: FeedbackItem.fromJsonArrayToFeedbackItems(json)) {
            feedbackItem.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/feedbackitems/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        FeedbackItem feedbackItem = FeedbackItem.findFeedbackItem(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (feedbackItem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        feedbackItem.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/feedbackitems", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<FeedbackItem> result = FeedbackItem.findAllFeedbackItems();
        return new ResponseEntity<String>(FeedbackItem.toJsonArray(result), headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/rest/feedbackitems/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        FeedbackItem feedbackItem = FeedbackItem.findFeedbackItem(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (feedbackItem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(feedbackItem.toJson(), headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/rest/feedbackitems", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        FeedbackItem feedbackItem = FeedbackItem.fromJsonToFeedbackItem(json);
        if (feedbackItem.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/feedbackitems/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (FeedbackItem feedbackItem: FeedbackItem.fromJsonArrayToFeedbackItems(json)) {
            if (feedbackItem.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

}
