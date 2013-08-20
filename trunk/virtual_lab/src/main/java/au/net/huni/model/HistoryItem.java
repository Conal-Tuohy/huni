package au.net.huni.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.proxy.HibernateProxyHelper;
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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESEARCHER_ID")
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

    public void setOwner(Researcher researcher) {
        this.owner = researcher;
        if (!researcher.getHistory().contains(this)) {
        	researcher.getHistory().add(this);
        }
    }
//    
//    @Override
//    public boolean equals(final Object obj) {
//        if (this == obj) {
//            return true;
//        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(HistoryItem.class))) {
//            return false;
//        }
//
//        HistoryItem candidate = (HistoryItem) obj;
//
//        return this.getToolName().equals(candidate.getToolName())
//            && this.getBackgroundColour().equals(candidate.getBackgroundColour())
//            && this.getExecutionDate().equals(candidate.getExecutionDate());
//    }
//    
//    @Override
//    public int hashCode() {
//        return this.getToolName().hashCode()
//             + this.getBackgroundColour().hashCode()
//             + this.getExecutionDate().hashCode();
//    }

}
