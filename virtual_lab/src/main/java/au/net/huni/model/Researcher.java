package au.net.huni.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findResearchersByUserNameEquals" })
public class Researcher {

    private static final ObjectFactory INSTITUTION_OBJECT_FACTORY = new ObjectFactory() {

        @SuppressWarnings("rawtypes")
		@Override
        public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
            Long id = Long.valueOf((String) value);
            return Institution.findInstitution(id);
        }
    };

    @NotNull
    @Column(unique = true)
    @Size(min = 5, max = 10)
    private String userName;

    @NotNull
    @Size(min = 1, max = 60)
    private String givenName;

    @NotNull
    @Size(min = 1, max = 60)
    private String familyName;

    @NotNull
    @Pattern(regexp = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$")
    private String emailAddress;

    @NotNull
    @ManyToOne
    private Institution institution;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<HistoryItem> history = new HashSet<HistoryItem>();

    @Size(max = 64, min = 64)
    private String password;

    private Boolean isAccountEnabled;

    @ManyToMany
    private Set<UserRole> roles = new HashSet<UserRole>();

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Calendar creationDate;

    public void setPassword(String clearTextPassword) {
        String encryptedPassword = DigestUtils.sha256Hex(clearTextPassword);
        this.password = encryptedPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.getFamilyName());
        buffer.append(", ");
        buffer.append(getGivenName());
        buffer.append(" (");
        buffer.append(getUserName());
        buffer.append(")");
        return buffer.toString();
    }

    public static String toJsonArray(Collection<au.net.huni.model.Researcher> collection) {
        return new JSONSerializer().exclude("*.class", "password", "encryptedPassword").serialize(collection);
    }

    public String toJson() {
        return new JSONSerializer().exclude("*.class", "password", "encryptedPassword").serialize(this);
    }

    public static au.net.huni.model.Researcher fromJsonToResearcher(String json) {
        return new JSONDeserializer<Researcher>().use(null, Researcher.class).use("institution", INSTITUTION_OBJECT_FACTORY).deserialize(json);
    }

    public static Collection<au.net.huni.model.Researcher> fromJsonArrayToResearchers(String json) {
        return new JSONDeserializer<List<Researcher>>().use(null, ArrayList.class).use("values", Researcher.class).use("institution", INSTITUTION_OBJECT_FACTORY).deserialize(json);
    }
}
