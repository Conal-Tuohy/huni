package au.net.huni.web;

import au.net.huni.model.ToolParameter;
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

@RequestMapping("/console/toolparameters")
@Controller
@RooWebScaffold(path = "toolparameters", formBackingObject = ToolParameter.class)
public class ToolParameterController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid ToolParameter toolParameter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolParameter);
            return "toolparameters/create";
        }
        uiModel.asMap().clear();
        toolParameter.persist();
        return "redirect:/console/toolparameters/" + encodeUrlPathSegment(toolParameter.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ToolParameter toolParameter = ToolParameter.findToolParameter(id);
        toolParameter.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/toolparameters";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid ToolParameter toolParameter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolParameter);
            return "toolparameters/update";
        }
        uiModel.asMap().clear();
        toolParameter.merge();
        return "redirect:/console/toolparameters/" + encodeUrlPathSegment(toolParameter.getId().toString(), httpServletRequest);
    }
}
