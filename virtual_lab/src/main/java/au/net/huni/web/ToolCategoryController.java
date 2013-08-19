package au.net.huni.web;

import au.net.huni.model.ToolCategory;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

//Access by default is restricted to the ADMIN role within the console webapp.
//This is over-ridden by annotations in this file.
//See webmvc-config.xml
@Controller
@RooWebScaffold(path = "toolcategorys", formBackingObject = ToolCategory.class)
public class ToolCategoryController {

	@RequestMapping(value = "/console/toolcategorys", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid ToolCategory toolCategory, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolCategory);
            return "toolcategorys/create";
        }
        uiModel.asMap().clear();
        toolCategory.persist();
        return "redirect:/console/toolcategorys/" + encodeUrlPathSegment(toolCategory.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/toolcategorys", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new ToolCategory());
        return "toolcategorys/create";
    }

	@RequestMapping(value = "/console/toolcategorys/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("toolcategory", ToolCategory.findToolCategory(id));
        uiModel.addAttribute("itemId", id);
        return "toolcategorys/show";
    }

	@RequestMapping(value = "/console/toolcategorys", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("toolcategorys", ToolCategory.findToolCategoryEntries(firstResult, sizeNo));
            float nrOfPages = (float) ToolCategory.countToolCategorys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("toolcategorys", ToolCategory.findAllToolCategorys());
        }
        return "toolcategorys/list";
    }

	@RequestMapping(value = "/console/toolcategorys", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid ToolCategory toolCategory, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolCategory);
            return "toolcategorys/update";
        }
        uiModel.asMap().clear();
        toolCategory.merge();
        return "redirect:/console/toolcategorys/" + encodeUrlPathSegment(toolCategory.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/toolcategorys/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, ToolCategory.findToolCategory(id));
        return "toolcategorys/update";
    }

	@RequestMapping(value = "/console/toolcategorys/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ToolCategory toolCategory = ToolCategory.findToolCategory(id);
        toolCategory.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/toolcategorys";
    }

	void populateEditForm(Model uiModel, ToolCategory toolCategory) {
        uiModel.addAttribute("toolCategory", toolCategory);
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
