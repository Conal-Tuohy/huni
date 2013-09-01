package au.net.huni.model;

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
        .transform(Constant.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(this);
    }

	public static String toJsonArray(Collection<Project> collection) {
        return new JSONSerializer()
        .exclude("*.class")
        .transform(Constant.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(collection);
    }

	public String toDeepJson() {
        return new JSONSerializer()
        .exclude("*.class")
        .include("dataSources")
        .transform(Constant.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(this);
    }

	public static String toDeepJsonArray(Collection<Project> collection) {
        return new JSONSerializer()
        .exclude("*.class")
        .include("dataSources")
        .transform(Constant.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(collection);
    }
}
