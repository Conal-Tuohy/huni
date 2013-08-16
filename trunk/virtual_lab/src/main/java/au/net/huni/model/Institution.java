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
@RooJpaActiveRecord
@RooJson
public class Institution {

	@NotNull
    @Column(unique = true)
    @Size(min = 5, max = 60)
    private String name;

    public Institution() {
		this("UNKNOWN");
	}

    public Institution(String name) {
		this.name = name;
	}

	public static String toJsonArray(Collection<Institution> collection) {
        return new JSONSerializer().exclude("*.class", "version").serialize(collection);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class", "version").serialize(this);
    }
}
