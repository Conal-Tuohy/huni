package au.net.huni.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import au.net.huni.json.CalendarTransformer;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class HistoryItem {

    @NotNull
    @Size(min = 2)
    private String toolName;

    @NotNull
    @Pattern(regexp = "#[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]")
    private String backgroundColour = "#FFFFFF";
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar executionDate = Calendar.getInstance();

    // Not null constraint causing problems during the tests 
    // @NotNull
    @ManyToOne
    private Researcher owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<ToolParameter> toolParameters = new HashSet<ToolParameter>();

	public static String toJsonArray(Collection<HistoryItem> collection) {
        return new JSONSerializer()
        .exclude("*.class", "owner", "version")
        .include("toolParameters")
        .transform(new CalendarTransformer("dd/MM/yyyy HH:mm:ss z"), Calendar.class)
        .serialize(collection);
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class", "owner", "version")
        .include("toolParameters")
        .transform(new CalendarTransformer("dd/MM/yyyy HH:mm:ss z"), Calendar.class)
        .serialize(this);
    }
}
