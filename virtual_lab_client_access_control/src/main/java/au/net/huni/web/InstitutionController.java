package au.net.huni.web;

import au.net.huni.model.Institution;
import java.io.UnsupportedEncodingException;
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
@RooWebScaffold(path = "institutions", formBackingObject = Institution.class)
@RooWebJson(jsonObject = Institution.class)
public class InstitutionController {
	
    @RequestMapping(value = "/console/institutions", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Institution institution, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, institution);
            return "institutions/create";
        }
        uiModel.asMap().clear();
        institution.persist();
        return "redirect:/console/institutions/" + encodeUrlPathSegment(institution.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/institutions", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Institution());
        return "institutions/create";
    }

    @RequestMapping(value = "/console/institutions/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("institution", Institution.findInstitution(id));
        uiModel.addAttribute("itemId", id);
        return "institutions/show";
    }

    @RequestMapping(value = "/console/institutions", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("institutions", Institution.findInstitutionEntries(firstResult, sizeNo));
            float nrOfPages = (float) Institution.countInstitutions() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("institutions", Institution.findAllInstitutions());
        }
        return "institutions/list";
    }

    @RequestMapping(value = "/console/institutions", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Institution institution, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, institution);
            return "institutions/update";
        }
        uiModel.asMap().clear();
        institution.merge();
        return "redirect:/console/institutions/" + encodeUrlPathSegment(institution.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/institutions/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Institution.findInstitution(id));
        return "institutions/update";
    }

    @RequestMapping(value = "/console/institutions/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Institution institution = Institution.findInstitution(id);
        institution.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/institutions";
    }

    void populateEditForm(Model uiModel, Institution institution) {
        uiModel.addAttribute("institution", institution);
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

	@RequestMapping(value = "/rest/institutions/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        Institution institution = Institution.findInstitution(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (institution == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(institution.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/institutions", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Institution> result = Institution.findAllInstitutions();
        return new ResponseEntity<String>(Institution.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/institutions", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Institution institution = Institution.fromJsonToInstitution(json);
        institution.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/institutions/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Institution institution: Institution.fromJsonArrayToInstitutions(json)) {
            institution.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/institutions", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Institution institution = Institution.fromJsonToInstitution(json);
        if (institution.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/institutions/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Institution institution: Institution.fromJsonArrayToInstitutions(json)) {
            if (institution.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/institutions/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        Institution institution = Institution.findInstitution(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (institution == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        institution.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
