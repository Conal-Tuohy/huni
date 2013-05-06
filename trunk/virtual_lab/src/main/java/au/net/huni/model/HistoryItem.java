package au.net.huni.model;

import java.awt.Color;
import java.util.Calendar;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class HistoryItem {

    @NotNull
    private String toolName;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar executionDate;

    @NotNull
    private Color backgroundColour;

    @NotNull
    private String json;

    @ManyToOne
    private Researcher owner;
}
