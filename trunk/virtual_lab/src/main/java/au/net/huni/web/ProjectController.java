package au.net.huni.web;

import au.net.huni.model.DataSource;
import au.net.huni.model.Project;
import au.net.huni.model.Researcher;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

//Access by default is restricted to the ADMIN role within the console webapp.
//This is over-ridden by annotations in this file.
//See webmvc-config.xml
@Controller
@RooWebScaffold(path = "projects", formBackingObject = Project.class)
public class ProjectController {

	@RequestMapping(value = "/console/projects", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Project project, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, project);
            return "projects/create";
        }
        uiModel.asMap().clear();
        // New project with existing researcher.
        Researcher researcher = project.getOwner();
        if (researcher != null) {
        	// Set the reverse relation, so the researcher knows about the project.
        	researcher.getProjects().add(project);
        }
        project.persist();
        return "redirect:/console/projects/" + encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/projects", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Project());
        return "projects/create";
    }

	@RequestMapping(value = "/console/projects/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("project", Project.findProject(id));
        uiModel.addAttribute("itemId", id);
        return "projects/show";
    }

	@RequestMapping(value = "/console/projects", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("projects", Project.findProjectEntries(firstResult, sizeNo));
            float nrOfPages = (float) Project.countProjects() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("projects", Project.findAllProjects());
        }
        addDateTimeFormatPatterns(uiModel);
        return "projects/list";
    }

	@RequestMapping(value = "/console/projects", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Project project, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, project);
            return "projects/update";
        }
        // Do not allow owner to be changed.
        Researcher newOwner = project.getOwner();
        Researcher oldOwner = Project.findProject(project.getId()).getOwner();
        if (newOwner != oldOwner) {
        	bindingResult.addError(new FieldError("project", "owner", "The owner of the project must not be changed. "));
            populateEditForm(uiModel, project);
            return "projects/update";
        }
        
        uiModel.asMap().clear();
        project.merge();
        return "redirect:/console/projects/" + encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/projects/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Project.findProject(id));
        return "projects/update";
    }

	@RequestMapping(value = "/console/projects/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Project project = Project.findProject(id);
        project.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/projects";
    }

	void populateEditForm(Model uiModel, Project project) {
        uiModel.addAttribute("project", project);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("researchers", Researcher.findAllResearchers());
        uiModel.addAttribute("datasources", DataSource.findAllDataSources());
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("project_startdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
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
}
