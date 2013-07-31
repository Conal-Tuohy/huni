package au.net.huni.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import au.net.huni.model.HistoryItem;
import au.net.huni.model.Researcher;

@Controller
@RooWebScaffold(path = "researchers", formBackingObject = Researcher.class)
@RooWebJson(jsonObject = Researcher.class)
public class ResearcherController {

	@RequestMapping(value = "/console/researchers", method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Researcher researcher,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, researcher);
			return "researchers/create";
		}
		uiModel.asMap().clear();		
		researcher.persist();
		return "redirect:/console/researchers/"
				+ encodeUrlPathSegment(researcher.getId().toString(),
						httpServletRequest);
	}

    @RequestMapping(value = "/console/researchers/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Researcher researcher = Researcher.findResearcher(id);
        researcher.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/researchers";
    }

    @RequestMapping(value = "/console/researchers", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Researcher researcher, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, researcher);
            return "researchers/update";
        }
        uiModel.asMap().clear();
        
        // Prevent modification of the password saved in the database.
        Researcher savedResearcher = Researcher.findResearcher(researcher.getId());
        researcher.setEncryptedPassword(savedResearcher.getPassword());
        researcher.merge();
        return "redirect:/console/researchers/" + encodeUrlPathSegment(researcher.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/researchers", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Researcher());
        return "researchers/create";
    }

	@RequestMapping(value = "/console/researchers/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("researcher", Researcher.findResearcher(id));
        uiModel.addAttribute("itemId", id);
        return "researchers/show";
    }

	@RequestMapping(value = "/console/researchers", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("researchers", Researcher.findResearcherEntries(firstResult, sizeNo));
            float nrOfPages = (float) Researcher.countResearchers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("researchers", Researcher.findAllResearchers());
        }
        return "researchers/list";
    }

	@RequestMapping(value = "/console/researchers/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Researcher.findResearcher(id));
        return "researchers/update";
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

	void populateEditForm(Model uiModel, Researcher researcher) {
        uiModel.addAttribute("researcher", researcher);
        uiModel.addAttribute("historyitems", HistoryItem.findAllHistoryItems());
    }
	
	// The REST access points do not need to be exposed.

    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        return null;
    }

    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        return null;
    }

    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        return null;
    }

    public ResponseEntity<String> listJson() {
        return null;
    }

    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        return null;
    }

    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        return null;
    }

    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        return null;
    }

    public ResponseEntity<String> jsonFindResearchersByUserNameEquals(@RequestParam("userName") String userName) {
        return null;
    }
}
