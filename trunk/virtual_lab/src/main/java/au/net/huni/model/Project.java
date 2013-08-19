package au.net.huni.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 64)
    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar startDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="PROJECT_ID", referencedColumnName="ID")
    private Set<DataSource> dataSources = new HashSet<DataSource>();
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(Project.class))) {
            return false;
        }

        Project candidate = (Project) obj;

        return this.getName().equals(candidate.getName())
                && this.getStartDate().equals(candidate.getStartDate())
            ;
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode()
                + this.getStartDate().hashCode()
             ;
    }
}
