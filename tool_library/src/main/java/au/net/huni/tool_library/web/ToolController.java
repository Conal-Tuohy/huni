package au.net.huni.tool_library.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import au.net.huni.tool_library.model.Tool;

@RequestMapping("/tools")
@Controller
@RooWebScaffold(path = "tools", formBackingObject = Tool.class)
public class ToolController {

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Tool tool, BindingResult bindingResult, Model uiModel,
    		@RequestParam("content") CommonsMultipartFile content,
    		HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, tool);
            return "tools/create";
        }

        File dest = new File("/tmp/" + content.getOriginalFilename());
        try {
           content.transferTo(dest);
           tool.setFilesize(content.getSize());
           tool.setFilepath(dest.getAbsolutePath());
           tool.setContentType(content.getContentType());
        } catch (Exception e) {
           e.printStackTrace();
           return "mediauploads/create";
        }

        uiModel.asMap().clear();
        tool.persist();
        return "redirect:/tools/" + encodeUrlPathSegment(tool.getId().toString(), httpServletRequest);
    }
}
