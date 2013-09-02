package au.net.huni.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import static au.net.huni.json.Transformer.*;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Project {

    @ManyToOne
    private Researcher owner;

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 64)
    private String name = RandomStringUtils.random(10);

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar startDate = Calendar.getInstance();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID")
    private Set<DataSource> dataSources = new HashSet<DataSource>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(Project.class))) {
            return false;
        }
        Project candidate = (Project) obj;
        return this.getName().equals(candidate.getName()) && this.getStartDate().equals(candidate.getStartDate());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.getStartDate().hashCode() * 37;
    }

    public String toString() {
        return getName();
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class")
        .transform(CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(this);
    }

	public static String toJsonArray(Collection<Project> collection) {
        return new JSONSerializer()
        .exclude("*.class")
        .transform(CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(collection);
    }

	public String toDeepJson() {
        return new JSONSerializer()
        .exclude("*.class")
        .include("dataSources")
        .transform(CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(this);
    }

	public static String toDeepJsonArray(Collection<Project> collection) {
        return new JSONSerializer()
        .exclude("*.class")
        .include("dataSources")
        .transform(CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(collection);
    }
	
	public static class Summary {
		
		private String userName;
	    private String projectName;
	    private Calendar startDate;


	    public Summary() {
			super();
		}

	    public Summary(String userName, String projectName, Calendar startDate) {
			super();
			this.userName = userName;
			this.projectName = projectName;
			this.startDate = startDate;
		}

		
	    public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public Calendar getStartDate() {
			return startDate;
		}

		public void setStartDate(Calendar startDate) {
			this.startDate = startDate;
		}

		public static Summary fromJsonToSummary(String json) {
	        return new JSONDeserializer<Summary>().use("startDate", CALENDAR_TRANSFORMER).use(null, Summary.class).deserialize(json);
	    }
	    
	}
}
