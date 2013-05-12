package au.net.huni.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
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
}
