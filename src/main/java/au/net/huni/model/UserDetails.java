package au.net.huni.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
}
