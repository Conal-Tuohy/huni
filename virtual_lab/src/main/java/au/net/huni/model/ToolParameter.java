package au.net.huni.model;

import java.util.Collection;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ToolParameter {

    @ManyToOne
    private HistoryItem owner;

    @NotNull
    @Size(min = 2)
    private String name  = RandomStringUtils.random(10);

    private String amount = "";

    private int displayOrder = 0;

	public static String toJsonArray(Collection<ToolParameter> collection) {
        return new JSONSerializer()
        .exclude("*.class", "owner")
        .serialize(collection);
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class", "owner")
        .serialize(this);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(ToolParameter.class))) {
            return false;
        }

        ToolParameter candidate = (ToolParameter) obj;

        return this.getName().equals(candidate.getName())
                && this.getAmount().equals(candidate.getAmount())
                && this.getDisplayOrder() == candidate.getDisplayOrder()
            ;
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode()
                + this.getAmount().hashCode() *37
                + this.getDisplayOrder() * 57
             ;
    }

    public String toString() {
        return getName();
    }
}
