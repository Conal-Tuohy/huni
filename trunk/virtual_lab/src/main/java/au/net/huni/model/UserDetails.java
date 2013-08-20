package au.net.huni.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.proxy.HibernateProxyHelper;

public class UserDetails {

    @NotNull
    @Size(max = 128)
    private String userName;

    @NotNull
    @Size(max = 256)
    private String givenName;

    @NotNull
    @Size(max = 256)
    private String familyName;

    // This field is expected to be set with an sha-256 encoded password
    // and so needs to be exactly 64 characters. 
    @Size(max = 64, min = 64)
    private String password;

    private Boolean isAccountEnabled;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<UserRole> roles = new HashSet<UserRole>();

    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
//
//	@Override
//    public boolean equals(final Object obj) {
//        if (this == obj) {
//            return true;
//        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(UserDetails.class))) {
//            return false;
//        }
//
//        UserDetails candidate = (UserDetails) obj;
//
//        return this.getUserName().equals(candidate.getUserName())
//            ;
//    }
//    
//    @Override
//    public int hashCode() {
//        return this.getUserName().hashCode()
//             ;
//    }
}
