package au.net.huni.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ToolCategory {

    @NotNull
    @Column(unique = true)
    @Size(min = 5, max = 64)
    private String name = RandomStringUtils.random(10);

	public String toString() {
        return this.name;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(ToolCategory.class))) {
            return false;
        }

        ToolCategory candidate = (ToolCategory) obj;

        return this.getName().equals(candidate.getName())
            ;
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode()
             ;
    }
}
