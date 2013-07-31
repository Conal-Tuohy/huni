package au.net.huni.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
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

}
