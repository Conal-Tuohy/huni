package au.net.huni.web;

import au.net.huni.model.Researcher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/console/researchers")
@Controller
@RooWebScaffold(path = "researchers", formBackingObject = Researcher.class)
public class ResearcherController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Researcher researcher, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, researcher);
            return "researchers/create";
        }
        uiModel.asMap().clear();
        researcher.persist();
        return "redirect:/console/researchers/" + encodeUrlPathSegment(researcher.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Researcher researcher = Researcher.findResearcher(id);
        researcher.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/researchers";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Researcher researcher, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, researcher);
            return "researchers/update";
        }
        uiModel.asMap().clear();
        researcher.merge();
        return "redirect:/console/researchers/" + encodeUrlPathSegment(researcher.getId().toString(), httpServletRequest);
    }
}
