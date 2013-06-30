package au.net.huni.web;

import au.net.huni.model.Institution;
import au.net.huni.model.Registration;
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
@RooWebScaffold(path = "registrations", formBackingObject = Registration.class)
@RooWebJson(jsonObject = Registration.class)
public class RegistrationController {

    @RequestMapping(value = "/console/registrations", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Registration registration, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, registration);
            return "registrations/create";
        }
        uiModel.asMap().clear();
        registration.persist();
        return "redirect:/console/registrations/" + encodeUrlPathSegment(registration.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/registrations", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Registration());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (Institution.countInstitutions() == 0) {
            dependencies.add(new String[] { "institution", "institutions" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "registrations/create";
    }

    @RequestMapping(value = "/console/registrations/{id}",  produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("registration", Registration.findRegistration(id));
        uiModel.addAttribute("itemId", id);
        return "registrations/show";
    }

    @RequestMapping(value = "/console/registrations", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("registrations", Registration.findRegistrationEntries(firstResult, sizeNo));
            float nrOfPages = (float) Registration.countRegistrations() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("registrations", Registration.findAllRegistrations());
        }
        addDateTimeFormatPatterns(uiModel);
        return "registrations/list";
    }

    @RequestMapping(value = "/console/registrations", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Registration registration, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, registration);
            return "registrations/update";
        }
        uiModel.asMap().clear();
        registration.merge();
        return "redirect:/console/registrations/" + encodeUrlPathSegment(registration.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/registrations/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Registration.findRegistration(id));
        return "registrations/update";
    }

    @RequestMapping(value = "/console/registrations/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Registration registration = Registration.findRegistration(id);
        registration.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/registrations";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("registration_applicationdate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("registration_approvaldate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, Registration registration) {
        uiModel.addAttribute("registration", registration);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("institutions", Institution.findAllInstitutions());
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

	@RequestMapping(value = "/rest/registrations/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        Registration registration = Registration.findRegistration(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (registration == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(registration.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/registrations", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Registration> result = Registration.findAllRegistrations();
        return new ResponseEntity<String>(Registration.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/registrations", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Registration registration = Registration.fromJsonToRegistration(json);
        registration.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/registrations/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Registration registration: Registration.fromJsonArrayToRegistrations(json)) {
            registration.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/registrations", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Registration registration = Registration.fromJsonToRegistration(json);
        if (registration.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/registrations/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Registration registration: Registration.fromJsonArrayToRegistrations(json)) {
            if (registration.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/registrations/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        Registration registration = Registration.findRegistration(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (registration == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        registration.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
