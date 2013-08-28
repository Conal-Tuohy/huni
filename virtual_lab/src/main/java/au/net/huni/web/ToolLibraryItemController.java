package au.net.huni.web;

import au.net.huni.model.ToolLibraryItem;
import au.net.huni.model.ToolCategory;
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
@RooWebScaffold(path = "toollibraryitems", formBackingObject = ToolLibraryItem.class)
public class ToolLibraryItemController {

	@RequestMapping(value = "/console/toollibraryitems", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid ToolLibraryItem toolLibraryItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolLibraryItem);
            return "toollibraryitems/create";
        }
        uiModel.asMap().clear();
        toolLibraryItem.persist();
        return "redirect:/console/toollibraryitems/" + encodeUrlPathSegment(toolLibraryItem.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/toollibraryitems", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new ToolLibraryItem());
        return "toollibraryitems/create";
    }

	@RequestMapping(value = "/console/toollibraryitems/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("toollibraryitem", ToolLibraryItem.findToolLibraryItem(id));
        uiModel.addAttribute("itemId", id);
        return "toollibraryitems/show";
    }

	@RequestMapping(value = "/console/toollibraryitems", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("toollibraryitems", ToolLibraryItem.findToolLibraryItemEntries(firstResult, sizeNo));
            float nrOfPages = (float) ToolLibraryItem.countToolLibraryItems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("toollibraryitems", ToolLibraryItem.findAllToolLibraryItems());
        }
        addDateTimeFormatPatterns(uiModel);
        return "toollibraryitems/list";
    }

	@RequestMapping(value = "/console/toollibraryitems", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid ToolLibraryItem toolLibraryItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolLibraryItem);
            return "toollibraryitems/update";
        }
        uiModel.asMap().clear();
        toolLibraryItem.merge();
        return "redirect:/console/toollibraryitems/" + encodeUrlPathSegment(toolLibraryItem.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/toollibraryitems/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, ToolLibraryItem.findToolLibraryItem(id));
        return "toollibraryitems/update";
    }

	// TODO RR: prevent deletion of tools.
	// A tool may be in a researcher's toolkit.
	@RequestMapping(value = "/console/toollibraryitems/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ToolLibraryItem toolLibraryItem = ToolLibraryItem.findToolLibraryItem(id);
        toolLibraryItem.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/toollibraryitems";
    }

	void populateEditForm(Model uiModel, ToolLibraryItem toolLibraryItem) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("toolLibraryItem", toolLibraryItem);
        uiModel.addAttribute("toolcategorys", ToolCategory.findAllToolCategorys());
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

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("toolLibraryItem_creationdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
}
