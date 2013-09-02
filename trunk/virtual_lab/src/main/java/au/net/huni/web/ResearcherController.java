package au.net.huni.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
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

import au.net.huni.model.HistoryItem;
import au.net.huni.model.Institution;
import au.net.huni.model.Project;
import au.net.huni.model.Researcher;
import au.net.huni.model.ToolLibraryItem;
import au.net.huni.model.UserRole;

@Controller
@RooWebScaffold(path = "researchers", formBackingObject = Researcher.class)
@RooWebJson(jsonObject = Researcher.class)
@RooWebFinder
public class ResearcherController {

    static final Logger logger = Logger.getLogger(ResearcherController.class);

    @RequestMapping(value = "/console/researchers", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Researcher researcher, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, researcher);
            return "researchers/create";
        }
        uiModel.asMap().clear();
        researcher.getRoles().add(UserRole.findUserRolesByNameEquals("USER_ROLE").getSingleResult());
        researcher.persist();
        return "redirect:/console/researchers/" + encodeUrlPathSegment(researcher.getId().toString(), httpServletRequest);
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
        Researcher savedResearcher = Researcher.findResearcher(researcher.getId());
        researcher.setEncryptedPassword(savedResearcher.getPassword());
        researcher.merge();
        return "redirect:/console/researchers/" + encodeUrlPathSegment(researcher.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/researchers", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Researcher());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (Institution.countInstitutions() == 0) {
            dependencies.add(new String[] { "institution", "institutions" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "researchers/create";
    }

    @RequestMapping(value = "/console/researchers/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
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
        addDateTimeFormatPatterns(uiModel);
        return "researchers/list";
    }

    @RequestMapping(value = "/console/researchers/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Researcher.findResearcher(id));
        return "researchers/update";
    }

	@RequestMapping(value = "/console/researchers", params = { "find=ByUserNameEquals", "form" }, method = RequestMethod.GET)
    public String findResearchersByUserNameEqualsForm(Model uiModel) {
        return "researchers/findResearchersByUserNameEquals";
    }

	@RequestMapping(value = "/console/researchers", params = "find=ByUserNameEquals", method = RequestMethod.GET)
    public String findResearchersByUserNameEquals(@RequestParam("userName") String userName, Model uiModel) {
        uiModel.addAttribute("researchers", Researcher.findResearchersByUserNameEquals(userName).getResultList());
        return "researchers/list";
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

    void populateEditForm(Model uiModel, Researcher researcher) {
        uiModel.addAttribute("researcher", researcher);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("institutions", Institution.findAllInstitutions());
        uiModel.addAttribute("historyitems", HistoryItem.findAllHistoryItems());
        uiModel.addAttribute("userroles", UserRole.findAllUserRoles());
        uiModel.addAttribute("toolcatalogitems", ToolLibraryItem.findAllToolLibraryItems());
        uiModel.addAttribute("projects", Project.findAllProjects());
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("researcher_creationdate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }

    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        return null;
    }

    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        return null;
    }

    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
        return null;
    }

    public ResponseEntity<java.lang.String> listJson() {
        return null;
    }

    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
        return null;
    }

    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        return null;
    }

    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        return null;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/rest/researchers/{items}", params = "find=ByUserNameEquals", method = RequestMethod.GET, headers = "Accept=application/json")    
    @ResponseBody
    public ResponseEntity<java.lang.String> jsonFindResearchersByUserNameEquals(@PathVariable("items") String items, @RequestParam("userName") String userName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        String jsonContent = null;
    	Researcher researcher = finderExistingResearcher(userName);
    	if (researcher == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
    	}
        if ("projects".equals(items)) {       	
	    	jsonContent = Project.toDeepJsonArray(researcher.getProjects());
        } else if ("tools".equals(items)) {
	    	jsonContent = ToolLibraryItem.toDeepJsonArray(researcher.getToolkit());
	    } else if ("notebook".equals(items)) {
	    	jsonContent = HistoryItem.toJsonArray(researcher.getHistory());
	    } else  if ("info".equals(items)) {
	    	jsonContent = researcher.toDeepJson();
	    } else {
            return new ResponseEntity<String>(new ErrorResponse("Operation on researcher unrecognised. ").toJson(), headers, HttpStatus.OK);
	    }
        return new ResponseEntity<String>(jsonContent, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/rest/researchers/addProject", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> jsonForResearcherAddProject(@RequestBody String requestJson, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = createJsonHeader();
        Project.Summary projectSummary =  Project.Summary.fromJsonToSummary(requestJson);
     	Researcher researcher = finderExistingResearcher(projectSummary.getUserName());
     	if (researcher == null) {
             return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
     	} else {
     		Project project = new Project();
     		project.setOwner(researcher);
     		researcher.getProjects().add(project);
     		project.setName(projectSummary.getProjectName());
     		project.setStartDate(projectSummary.getStartDate());
     		project.persist();
     	}
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	protected Researcher finderExistingResearcher(String userName) {
		Researcher existingResearcher = null;
        try {
        	existingResearcher = Researcher.findResearchersByUserNameEquals(userName).getSingleResult();
        } catch (NoResultException ignore) {
        	String message = "A researcher with that username was not found: " + userName;
        	logger.info(message);
        } catch (EmptyResultDataAccessException ignore) {
        	String message = "A researcher with that username was not found: " + userName;
        	logger.info(message);
        } catch (Exception ignore) {
        	String message = "A researcher with that username was not found: " + userName;
        	logger.info(message);
        }         
        return existingResearcher;
	}
	
	protected HttpHeaders createJsonHeader() {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
		return headers;
	}

}
