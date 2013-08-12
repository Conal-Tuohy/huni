package au.net.huni.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findUserRolesByNameEquals" })
public class UserRole {
	
    @NotNull
    @Column(unique = true)
    @Size(max = 50)
    private String name = "";
    
    // TODO RR: write a test to ensure the ownership is correct.
    @ManyToMany
    private Set<Researcher> researchers = new HashSet<Researcher>();

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
