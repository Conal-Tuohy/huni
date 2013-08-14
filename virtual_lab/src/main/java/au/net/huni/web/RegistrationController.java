package au.net.huni.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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

import au.net.huni.model.Institution;
import au.net.huni.model.Registration;
import au.net.huni.model.RegistrationStatus;
import au.net.huni.model.Researcher;
import au.net.huni.model.UserRole;

@Controller
@RooWebScaffold(path = "registrations", formBackingObject = Registration.class)
@RooWebJson(jsonObject = Registration.class)
public class RegistrationController {

    static final Logger logger = Logger.getLogger(RegistrationController.class);

    @Autowired
    private transient MailSender mailTemplate;

    @RequestMapping(value = "/console/registrations", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Registration registration, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, registration);
            return "registrations/create";
        }
        uiModel.asMap().clear();
        Calendar currentDate = Calendar.getInstance();
        registration.setApplicationDate(currentDate);
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

    @RequestMapping(value = "/console/registrations/{id}", produces = "text/html")
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
        Registration existingRegistration = findExistingRegistration(registration);
        if (isApproval(registration, existingRegistration)) {
            approve(registration);
        } else if (isRejection(registration, existingRegistration)) {
            reject(registration);
        }
        updateRegistration(registration);
        return "redirect:/console/registrations/" + encodeUrlPathSegment(registration.getId().toString(), httpServletRequest);
    }

    protected Registration findExistingRegistration(Registration registration) {
        return Registration.findRegistration(registration.getId());
    }

    protected void updateRegistration(Registration registration) {
        registration.merge();
    }

    protected boolean isApproval(Registration updatedRegistration, Registration existingRegistration) {
        if (updatedRegistration == null || existingRegistration == null) {
            return false;
        }
        RegistrationStatus proposedStatus = updatedRegistration.getStatus();
        RegistrationStatus existingStatus = existingRegistration.getStatus();
        return proposedStatus == RegistrationStatus.APPROVED && existingStatus == RegistrationStatus.PENDING;
    }

    protected boolean isRejection(Registration updatedRegistration, Registration existingRegistration) {
        if (updatedRegistration == null || existingRegistration == null) {
            return false;
        }
        RegistrationStatus proposedStatus = updatedRegistration.getStatus();
        RegistrationStatus existingStatus = existingRegistration.getStatus();
        return proposedStatus == RegistrationStatus.REJECTED && existingStatus == RegistrationStatus.PENDING;
    }

    protected Researcher approve(Registration updatedRegistration) {
        Researcher newResearcher = null;
        try {
            String userName = updatedRegistration.getUserName();
            newResearcher = new Researcher();
            newResearcher.setUserName(userName);
            newResearcher.setGivenName(updatedRegistration.getGivenName());
            newResearcher.setFamilyName(updatedRegistration.getFamilyName());
            String emailAddress = updatedRegistration.getEmailAddress();
			newResearcher.setEmailAddress(emailAddress);
            newResearcher.setInstitution(updatedRegistration.getInstitution());
            Calendar calendar = Calendar.getInstance();
            newResearcher.setCreationDate(calendar);
            newResearcher.setIsAccountEnabled(true);
            UserRole userRole = assignDefaultRole();
            newResearcher.getRoles().add(userRole);
            updatedRegistration.setApprovalDate(calendar);
            persistResearcher(newResearcher);
            String message = "Thank you for your interest in the HuNI Project. Your application has been accepted. ";
            sendMessage("huniproject@gmail.com", "HuNI Virtual Lab: Registration Approval", emailAddress, message);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to save the new researcher", exception);
        }
        return newResearcher;
    }

    protected Researcher reject(Registration updatedRegistration) {
        Researcher newResearcher = null;
        Calendar calendar = Calendar.getInstance();
        updatedRegistration.setApprovalDate(calendar);
        String emailAddress = updatedRegistration.getEmailAddress();
        String message = "Thank you for your interest in the HuNI Project. Your application has been rejected. ";
        sendMessage("huniproject@gmail.com", "HuNI Virtual Lab: Registration Rejection", emailAddress, message);
        return newResearcher;
    }

    protected UserRole assignDefaultRole() {
        UserRole userRole = null;
        try {
            userRole = UserRole.findUserRolesByNameEquals("USER_ROLE").getSingleResult();
        } catch (NoResultException missingRole) {
            throw new RuntimeException("Failed to save the new researcher, since default role is missing", missingRole);
        } catch (EmptyResultDataAccessException missingRole) {
            throw new RuntimeException("Failed to save the new researcher, since default role is missing", missingRole);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to find the default role", exception);
        }
        return userRole;
    }

    protected void persistResearcher(Researcher newResearcher) {
        newResearcher.persist();
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

    void populateEditForm(Model uiModel, Registration registration) {
        uiModel.addAttribute("registration", registration);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("institutions", Institution.findAllInstitutions());
        uiModel.addAttribute("registrationstatuses", Arrays.asList(RegistrationStatus.values()));
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("registration_applicationdate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("registration_approvaldate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
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
    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Registration registration = Registration.findRegistration(id);
        if (registration == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(registration.toJson(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/registrations", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Registration> result = Registration.findAllRegistrations();
        return new ResponseEntity<String>(Registration.toJsonArray(result), headers, HttpStatus.OK);
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/rest/registrations", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            json = prepareToInjectInstitution(json);
            Registration registration = Registration.fromJsonToRegistration(json);
            Calendar currentDate = Calendar.getInstance();
            registration.setApplicationDate(currentDate);
            registration.setStatus(RegistrationStatus.PENDING);
            registration.persist();
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (Throwable exception) {
            logger.error("createFromJson", exception);
            return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/rest/registrations/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        json = prepareToInjectInstitution(json);
        Calendar currentDate = Calendar.getInstance();
        for (Registration registration : Registration.fromJsonArrayToRegistrations(json)) {
            registration.setApplicationDate(currentDate);
            registration.setStatus(RegistrationStatus.PENDING);
            registration.persist();
        }
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/rest/registrations", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        json = prepareToInjectInstitution(json);
        Registration registration = Registration.fromJsonToRegistration(json);
        if (registration.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/registrations/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        json = prepareToInjectInstitution(json);
        for (Registration registration : Registration.fromJsonArrayToRegistrations(json)) {
            if (registration.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/registrations/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Registration registration = Registration.findRegistration(id);
        if (registration == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        registration.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    private String prepareToInjectInstitution(String json) {
        json = json.replaceAll("institutionId", "institution");
        return json;
    }

    protected void sendMessage(String mailFrom, String subject, String mailTo, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setSubject(subject);
        mailMessage.setTo(mailTo);
        mailMessage.setText(message);
        getMailTemplate().send(mailMessage);
    }

	public MailSender getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(MailSender mailTemplate) {
		this.mailTemplate = mailTemplate;
	}
}
