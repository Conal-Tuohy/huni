package au.net.huni.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

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
        .exclude("*.class")
        .transform(au.net.huni.json.Transformer.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(collection);
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class")
        .transform(au.net.huni.json.Transformer.CALENDAR_TRANSFORMER, Calendar.class)
        .serialize(this);
    }

    @Override
	public String toString() {
		Date date = getFeedbackDate().getTime();
		String formattedDateTime = Constant.DATE_FORMATTER.format(date);

		StringBuilder builder = new StringBuilder();
		builder.append("Context: ").append(getContext())
		.append(", Date: ").append(formattedDateTime)
		.append(", Origin: ").append(getVisitorIpAddress());
        return builder.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (!(HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(FeedbackItem.class))) {
            return false;
        }

        FeedbackItem candidate = (FeedbackItem) obj;

        return this.getContext().equals(candidate.getContext())
                && this.getFeedbackDate().equals(candidate.getFeedbackDate())
                && this.getVisitorIpAddress().equals(candidate.getVisitorIpAddress())
            ;
    }
    
    @Override
    public int hashCode() {
        return this.getContext().hashCode()
                + this.getFeedbackDate().hashCode() * 37
                + this.getVisitorIpAddress().hashCode() * 57
             ;
    }
}
