package au.net.huni.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findResearchersByUserNameEquals" })
public class Researcher {

    @NotNull
    @Size(max = 128)
    private String userName;

    @NotNull
    @Size(max = 256)
    private String givenName;

    @NotNull
    @Size(max = 256)
    private String familyName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<HistoryItem> history = new HashSet<HistoryItem>();

    // This field is expected to be set with an sha-256 encoded password
    // and so needs to be exactly 64 characters. 
    @Size(max = 64, min = 64)
    private String password;

    private Boolean isAccountEnabled;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<UserRole> roles = new HashSet<UserRole>();

	// Accepts a plain text password and stores it in encrypted form
	public void setPassword(String clearTextPassword) {
        // Encrypt the password to be saved in the database.
		// TODO RR: where do we handle the salt?
		String encryptedPassword = DigestUtils.sha256Hex(clearTextPassword);
        this.password = encryptedPassword;
    }

	// Returns the encrypted password
	public String getPassword() {
        return this.password;
    }

	// Used to set the encrypted password for preservation purposes.
	public void setEncryptedPassword(String encryptedPassword) {
        this.password = encryptedPassword;
	}
}
