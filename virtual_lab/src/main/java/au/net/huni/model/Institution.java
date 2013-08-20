package au.net.huni.model;

import flexjson.JSONSerializer;
import java.util.Collection;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.proxy.HibernateProxyHelper;
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
    private String code;

    @NotNull
    @Column(unique = false)
    @Size(min = 5, max = 60)
    private String name;

    public Institution() {
        this("?", "UNKNOWN");
    }

//    public Institution(String code) {
//        this.code = code;
//    }

    public Institution(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String toJsonArray(Collection<au.net.huni.model.Institution> collection) {
        return new JSONSerializer().exclude("*.class", "version").serialize(collection);
    }

    public String toJson() {
        return new JSONSerializer().exclude("*.class", "version").serialize(this);
    }
//    
//    @Override
//    public boolean equals(final Object obj) {
//        if (this == obj) {
//            return true;
//        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(Institution.class))) {
//            return false;
//        }
//
//        Institution candidate = (Institution) obj;
//
//        return this.getCode().equals(candidate.getCode())
//            ;
//    }
//    
//    @Override
//    public int hashCode() {
//        return this.getCode().hashCode()
//             ;
//    }
}
