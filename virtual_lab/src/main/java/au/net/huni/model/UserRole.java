package au.net.huni.model;

import flexjson.JSONSerializer;
import java.util.Collection;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private String name = "";

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
}
