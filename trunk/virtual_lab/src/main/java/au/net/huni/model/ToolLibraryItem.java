package au.net.huni.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class ToolLibraryItem {
	
	private final static String BASE_THUMBNAIL_URL = "/virtual_lab/img/tool-library/";

    @NotNull
    @Column(unique = true)
    @Size(min = 5, max = 64)
    private String name = RandomStringUtils.random(10);

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar creationDate = Calendar.getInstance();

    @NotNull
    private String softwareVersion;

    @NotNull
    private String author;

    private String description = "";

    @NotNull
    private String thumbnailFileName = "default-thumbnail.png";

    @NotNull
    private String url = "/";

    @NotNull
    @ManyToMany
    private Set<ToolCategory> categories = new HashSet<ToolCategory>();


	public String getThumbnailUrl() {
        return BASE_THUMBNAIL_URL + this.thumbnailFileName;
    }
	
	public void setThumbnailUrl(String thumbnailUrl) {
		throw new NotImplementedException();
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class")
        .transform(Constant.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(this);
    }

	public String toDeepJson() {
        return new JSONSerializer()
        .exclude("*.class")
        .include("categories.name")
        .transform(Constant.CATEGORY_TRANSFORMER, ToolCategory.class)
        .transform(Constant.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(this);
    }

	public static ToolLibraryItem fromJsonToToolLibraryItem(String json) {
        return new JSONDeserializer<ToolLibraryItem>().use(null, ToolLibraryItem.class).deserialize(json);
    }

	public static String toJsonArray(Collection<ToolLibraryItem> collection) {
        return new JSONSerializer()
        .exclude("*.class").include("categories")
        .transform(Constant.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(collection);
    }

	public static String toDeepJsonArray(Collection<ToolLibraryItem> collection) {
        return new JSONSerializer()
        .exclude("*.class")
        .include("categories")
        .transform(Constant.CATEGORY_TRANSFORMER, ToolCategory.class)
        .transform(Constant.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(collection);
	}

	public static Collection<ToolLibraryItem> fromJsonArrayToToolLibraryItems(String json) {
        return new JSONDeserializer<List<ToolLibraryItem>>().use(null, ArrayList.class).use("values", ToolLibraryItem.class).deserialize(json);
    }

    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(ToolLibraryItem.class))) {
            return false;
        }
        ToolLibraryItem candidate = (ToolLibraryItem) obj;
        return this.getName().equals(candidate.getName()) && this.getUrl().equals(candidate.getUrl());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.getUrl().hashCode() * 37;
    }
}
