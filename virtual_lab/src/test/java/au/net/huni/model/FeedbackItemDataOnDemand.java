package au.net.huni.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Configurable
@Component
@RooDataOnDemand(entity = FeedbackItem.class)
public class FeedbackItemDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<FeedbackItem> data;

	public FeedbackItem getNewTransientFeedbackItem(int index) {
        FeedbackItem obj = new FeedbackItem();
        setComment(obj, index);
        setContext(obj, index);
        setRating(obj, index);
        setFeedbackDate(obj, index);
        setVisitorIpAddress(obj, index);
        return obj;
    }

	public void setComment(FeedbackItem obj, int index) {
        String comment = "comment_" + index;
        obj.setComment(comment);
    }

	public void setContext(FeedbackItem obj, int index) {
        String context = "context_" + index;
        obj.setContext(context);
    }

	public void setRating(FeedbackItem obj, int index) {
        Rating rating = Rating.class.getEnumConstants()[0];
        obj.setRating(rating);
    }

	public FeedbackItem getSpecificFeedbackItem(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        FeedbackItem obj = data.get(index);
        Long id = obj.getId();
        return FeedbackItem.findFeedbackItem(id);
    }

	public FeedbackItem getRandomFeedbackItem() {
        init();
        FeedbackItem obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return FeedbackItem.findFeedbackItem(id);
    }

	public boolean modifyFeedbackItem(FeedbackItem obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = FeedbackItem.findFeedbackItemEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'FeedbackItem' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<FeedbackItem>();
        for (int i = 0; i < 10; i++) {
            FeedbackItem obj = getNewTransientFeedbackItem(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }

	public void setFeedbackDate(FeedbackItem obj, int index) {
        Calendar feedbackDate = Calendar.getInstance();
        obj.setFeedbackDate(feedbackDate);
    }

	public void setVisitorIpAddress(FeedbackItem obj, int index) {
        String visitorIpAddress = "visitorIpAddress_" + index;
        obj.setVisitorIpAddress(visitorIpAddress);
    }
}
