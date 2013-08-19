package au.net.huni.web;

import au.net.huni.model.DataSource;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/datasources")
@Controller
@RooWebScaffold(path = "datasources", formBackingObject = DataSource.class)
public class DataSourceController {
}
