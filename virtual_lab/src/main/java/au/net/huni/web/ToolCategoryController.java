package au.net.huni.web;

import au.net.huni.model.ToolCategory;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/toolcategorys")
@Controller
@RooWebScaffold(path = "toolcategorys", formBackingObject = ToolCategory.class)
public class ToolCategoryController {
}
