package au.net.huni.web;

import au.net.huni.model.HistoryItem;
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

@RequestMapping("/console/historyitems")
@Controller
@RooWebScaffold(path = "historyitems", formBackingObject = HistoryItem.class)
public class HistoryItemController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid HistoryItem historyItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, historyItem);
            return "historyitems/create";
        }
        uiModel.asMap().clear();
        historyItem.persist();
        return "redirect:/console/historyitems/" + encodeUrlPathSegment(historyItem.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        HistoryItem historyItem = HistoryItem.findHistoryItem(id);
        historyItem.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/historyitems";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid HistoryItem historyItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, historyItem);
            return "historyitems/update";
        }
        uiModel.asMap().clear();
        historyItem.merge();
        return "redirect:/console/historyitems/" + encodeUrlPathSegment(historyItem.getId().toString(), httpServletRequest);
    }
}
