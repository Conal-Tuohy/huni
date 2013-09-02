package au.net.huni.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import static au.net.huni.json.Transformer.*;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class DataSource {

    @ManyToOne
    private Project owner;

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 64)
    private String name = RandomStringUtils.random(10);

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar importDate = Calendar.getInstance();

    private String description;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(DataSource.class))) {
            return false;
        }
        DataSource candidate = (DataSource) obj;
        return this.getName().equals(candidate.getName()) && this.getImportDate().equals(candidate.getImportDate());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.getImportDate().hashCode() * 37;
    }

    public String toString() {
        return getName();
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class")
        .transform(CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(this);
    }

	public static DataSource fromJsonToDataSource(String json) {
        return new JSONDeserializer<DataSource>()
        		.use(null, DataSource.class)
        		.deserialize(json);
    }

	public static String toJsonArray(Collection<DataSource> collection) {
        return new JSONSerializer()
        .exclude("*.class")
        .transform(CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(collection);
    }

	public static Collection<DataSource> fromJsonArrayToDataSources(String json) {
        return new JSONDeserializer<List<DataSource>>()
        		.use(null, ArrayList.class)
        		.use("values", DataSource.class)
        		.deserialize(json);
    }
}
