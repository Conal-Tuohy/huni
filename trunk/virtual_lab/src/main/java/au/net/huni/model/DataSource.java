package au.net.huni.model;

import java.util.Calendar;
import javax.persistence.Column;
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
public class DataSource {

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 64)
    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar importDate;

    private String description;

    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(DataSource.class))) {
            return false;
        }

        DataSource candidate = (DataSource) obj;

        return this.getName().equals(candidate.getName())
            && this.getImportDate().equals(candidate.getImportDate());
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode()
             + this.getImportDate().hashCode();
    }
}
