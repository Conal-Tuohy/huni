package au.net.huni.model;

import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;

@RooJson
@RooWebJson(jsonObject = au.net.huni.model.RegistrationStatus.class)
public enum RegistrationStatus {

    PENDING, APPROVED, REJECTED;
}
