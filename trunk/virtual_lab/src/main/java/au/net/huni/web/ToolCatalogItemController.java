package au.net.huni.web;

import au.net.huni.model.ToolCatalogItem;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/toolcatalogitems")
@Controller
@RooWebScaffold(path = "toolcatalogitems", formBackingObject = ToolCatalogItem.class)
public class ToolCatalogItemController {
}
