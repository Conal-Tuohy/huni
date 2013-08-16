package au.net.huni.model;

import flexjson.JSONSerializer;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import au.net.huni.json.CalendarTransformer;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class FeedbackItem {

    private String context;

    @Enumerated
    private Rating rating;

    private String comment;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Calendar feedbackDate;

    private String visitorIpAddress;

	public static String toJsonArray(Collection<FeedbackItem> collection) {
        return new JSONSerializer()
        .exclude("*.class", "version")
        .transform(new CalendarTransformer("dd/MM/yyyy HH:mm:ss z"), Calendar.class)
        .serialize(collection);
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class", "version")
        .transform(new CalendarTransformer("dd/MM/yyyy HH:mm:ss z"), Calendar.class)
        .serialize(this);
    }
}
