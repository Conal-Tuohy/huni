package au.net.huni.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

import org.apache.commons.lang3.RandomStringUtils;
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
	
	private final static String dataTimePattern = "dd/MM/yyyy HH:mm:ss z";
	private final static SimpleDateFormat formatter = new SimpleDateFormat(dataTimePattern);

    @NotNull
    @Size(min = 2)
    // Set to random string so hashcode works even when the object is first created.
    // Otherwise contains and hence setOwner fails.
    private String toolName = RandomStringUtils.random(10);

    @NotNull
    @Pattern(regexp = "#[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]")
    private String backgroundColour = "#FFFFFF";
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar executionDate = Calendar.getInstance();

    // Not null constraint causing problems during the tests 
    // @NotNull
    @ManyToOne()
    @JoinColumn(name="RESEARCHER_ID", referencedColumnName="ID")
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
        // Ensure that we have a valid object before we execute contains.
        // Do this by initialising the tool name to some random string.
        if (!researcher.getHistory().contains(this)) {
        	researcher.getHistory().add(this);
        }
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(HistoryItem.class))) {
            return false;
        }

        HistoryItem candidate = (HistoryItem) obj;

        return this.getToolName().equals(candidate.getToolName())
            && this.getExecutionDate().equals(candidate.getExecutionDate());
    }
    
    @Override
    public int hashCode() {
        return this.getToolName().hashCode()
             + this.getExecutionDate().hashCode();
    }


	public String toString() {
		Date date = getExecutionDate().getTime();
		String formattedDateTime = formatter.format(date);
        return getToolName() + "(" + formattedDateTime + ")";
    }
}
