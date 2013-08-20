package au.net.huni.web;

import au.net.huni.model.DataSource;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
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
@RooWebScaffold(path = "datasources", formBackingObject = DataSource.class)
public class DataSourceController {

	@RequestMapping(value = "/console/datasources", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid DataSource dataSource, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, dataSource);
            return "datasources/create";
        }
        uiModel.asMap().clear();
        dataSource.persist();
        return "redirect:/console/datasources/" + encodeUrlPathSegment(dataSource.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/datasources", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new DataSource());
        return "datasources/create";
    }

	@RequestMapping(value = "/console/datasources/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("datasource", DataSource.findDataSource(id));
        uiModel.addAttribute("itemId", id);
        return "datasources/show";
    }

	@RequestMapping(value = "/console/datasources", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("datasources", DataSource.findDataSourceEntries(firstResult, sizeNo));
            float nrOfPages = (float) DataSource.countDataSources() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("datasources", DataSource.findAllDataSources());
        }
        addDateTimeFormatPatterns(uiModel);
        return "datasources/list";
    }

	@RequestMapping(value = "/console/datasources", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid DataSource dataSource, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, dataSource);
            return "datasources/update";
        }
        uiModel.asMap().clear();
        dataSource.merge();
        return "redirect:/console/datasources/" + encodeUrlPathSegment(dataSource.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/datasources/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, DataSource.findDataSource(id));
        return "datasources/update";
    }

	@RequestMapping(value = "/console/datasources/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        DataSource dataSource = DataSource.findDataSource(id);
        dataSource.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/datasources";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("dataSource_importdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, DataSource dataSource) {
        uiModel.addAttribute("dataSource", dataSource);
        addDateTimeFormatPatterns(uiModel);
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
