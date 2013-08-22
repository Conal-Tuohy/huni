package au.net.huni.model;

import flexjson.JSONSerializer;
import java.util.Collection;
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
@RooJpaActiveRecord(finders = { "findUserRolesByNameEquals" })
@RooJson
public class UserRole {

    @NotNull
    @Column(unique = true)
    @Size(max = 50)
    private String name = RandomStringUtils.random(10);

    public UserRole(String name) {
        super();
        this.name = name;
    }

    public UserRole() {
        this("");
    }

    public String toString() {
        return name;
    }

	public static String toJsonArray(Collection<UserRole> collection) {
        return new JSONSerializer()
        .exclude("*.class", "version")
        .serialize(collection);
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class", "version")
        .serialize(this);
    }
	
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(UserRole.class))) {
            return false;
        }

        UserRole candidate = (UserRole) obj;

        return this.getName().equals(candidate.getName())
            ;
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode()
             ;
    }
}
