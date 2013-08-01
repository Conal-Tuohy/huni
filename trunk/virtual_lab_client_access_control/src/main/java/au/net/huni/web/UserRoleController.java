package au.net.huni.web;

import au.net.huni.model.UserRole;
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

@Controller
@RooWebScaffold(path = "userroles", formBackingObject = UserRole.class)
public class UserRoleController {

	@RequestMapping(value = "/console/userroles", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UserRole userRole, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userRole);
            return "userroles/create";
        }
        uiModel.asMap().clear();
        userRole.persist();
        return "redirect:/console/userroles/" + encodeUrlPathSegment(userRole.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/userroles", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UserRole());
        return "userroles/create";
    }

	@RequestMapping(value = "/console/userroles/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("userrole", UserRole.findUserRole(id));
        uiModel.addAttribute("itemId", id);
        return "userroles/show";
    }

	@RequestMapping(value = "/console/userroles", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("userroles", UserRole.findUserRoleEntries(firstResult, sizeNo));
            float nrOfPages = (float) UserRole.countUserRoles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("userroles", UserRole.findAllUserRoles());
        }
        return "userroles/list";
    }

	@RequestMapping(value = "/console/userroles", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UserRole userRole, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userRole);
            return "userroles/update";
        }
        uiModel.asMap().clear();
        userRole.merge();
        return "redirect:/console/userroles/" + encodeUrlPathSegment(userRole.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/console/userroles/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, UserRole.findUserRole(id));
        return "userroles/update";
    }

	@RequestMapping(value = "/console/userroles/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserRole userRole = UserRole.findUserRole(id);
        userRole.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/userroles";
    }

	void populateEditForm(Model uiModel, UserRole userRole) {
        uiModel.addAttribute("userRole", userRole);
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
