package au.net.huni.model;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ToolParameter {

    @NotNull
    @Size(min = 2)
    private String name;

    private String amount;

    private int displayOrder;

    @NotNull
    @ManyToOne
    private HistoryItem owner;
}
