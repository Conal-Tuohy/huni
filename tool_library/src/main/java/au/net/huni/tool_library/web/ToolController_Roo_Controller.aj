// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.tool_library.web;

import au.net.huni.tool_library.model.Category;
import au.net.huni.tool_library.model.Tool;
import au.net.huni.tool_library.web.ToolController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ToolController_Roo_Controller {
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ToolController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Tool());
        return "tools/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String ToolController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("tool", Tool.findTool(id));
        uiModel.addAttribute("itemId", id);
        return "tools/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String ToolController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("tools", Tool.findToolEntries(firstResult, sizeNo));
            float nrOfPages = (float) Tool.countTools() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("tools", Tool.findAllTools());
        }
        return "tools/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ToolController.update(@Valid Tool tool, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, tool);
            return "tools/update";
        }
        uiModel.asMap().clear();
        tool.merge();
        return "redirect:/tools/" + encodeUrlPathSegment(tool.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String ToolController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Tool.findTool(id));
        return "tools/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String ToolController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Tool tool = Tool.findTool(id);
        tool.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/tools";
    }
    
    void ToolController.populateEditForm(Model uiModel, Tool tool) {
        uiModel.addAttribute("tool", tool);
        uiModel.addAttribute("categorys", Category.findAllCategorys());
    }
    
    String ToolController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
