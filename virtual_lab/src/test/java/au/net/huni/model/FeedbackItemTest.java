package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class FeedbackItemTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        FeedbackItem.countFeedbackItems();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, FeedbackItem.countFeedbackItems());
    }
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	FeedbackItem feedbackItem = new FeedbackItem();
    	feedbackItem.setComment("comment1");
    	feedbackItem.setRating(Rating.FOUR);
    	feedbackItem.setContext("/contact");
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );
		feedbackItem.setFeedbackDate(calendar);
		feedbackItem.setVisitorIpAddress("111.222.333.444");
    	
    	String actualJson = feedbackItem.toJson();
    	
    	assertTrue("JSON comment is correct", actualJson.contains("\"comment\":\"comment1\""));
    	assertTrue("JSON rating name is correct", actualJson.contains("\"rating\":\"FOUR\""));
    	assertTrue("JSON context is correct", actualJson.contains("\"context\":\"/contact\""));
    	assertTrue("JSON feedback date is correct", actualJson.contains("\"feedbackDate\":\"25/12/2013 18:30:45 EST\""));
    	assertTrue("JSON IP address is correct", actualJson.contains("\"visitorIpAddress\":\"111.222.333.444\""));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":"));
    }

    @Test
    public void testToString() {
    	Calendar today = Calendar.getInstance();
    	today.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	today.setTimeZone(timeZone);

    	FeedbackItem feedbackItem = new FeedbackItem();
    	feedbackItem.setContext("context0");
    	feedbackItem.setFeedbackDate(today);
    	feedbackItem.setRating(Rating.FOUR);
    	feedbackItem.setVisitorIpAddress("0.1.2.3");
    	assertEquals("Feedback toString is name.", "Context: context0, Date: 25/12/2013 18:30:45 EST, Origin: 0.1.2.3", feedbackItem.toString());
    }

    @Test
    public void testEquals() {
    	Calendar today = Calendar.getInstance();
    	today.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	today.setTimeZone(timeZone);

    	FeedbackItem feedbackItem0 = new FeedbackItem();
    	feedbackItem0.setContext("feedabackitem0");
    	feedbackItem0.setFeedbackDate(today);
    	feedbackItem0.setVisitorIpAddress("0.1.2.3");
    	feedbackItem0.setRating(Rating.FIVE);
    	feedbackItem0.setComment("My comment0");
    	
    	FeedbackItem feedbackItem1 = new FeedbackItem();
    	feedbackItem1.setContext("feedabackitem0");
    	feedbackItem1.setFeedbackDate(today);
    	feedbackItem1.setVisitorIpAddress("0.1.2.3");
    	feedbackItem1.setRating(Rating.FOUR);
    	feedbackItem1.setComment("My comment1");
    	
    	assertTrue("FeedbackItem equals is based on context, origin and date of feedback.", feedbackItem0.equals(feedbackItem1));
    }

    @Test
    public void testHashCode() {
    	Calendar today = Calendar.getInstance();
    	today.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	today.setTimeZone(timeZone);

    	FeedbackItem feedbackItem0 = new FeedbackItem();
    	feedbackItem0.setContext("feedabackitem0");
    	feedbackItem0.setFeedbackDate(today);
    	feedbackItem0.setVisitorIpAddress("0.1.2.3");
    	feedbackItem0.setRating(Rating.FIVE);
    	feedbackItem0.setComment("My comment0");
    	
    	FeedbackItem feedbackItem1 = new FeedbackItem();
    	feedbackItem1.setContext("feedabackitem0");
    	feedbackItem1.setFeedbackDate(today);
    	feedbackItem1.setVisitorIpAddress("0.1.2.3");
    	feedbackItem1.setRating(Rating.FOUR);
    	feedbackItem1.setComment("My comment1");
    	
    	assertEquals("FeedbackItem hashcode is based on context, origin and date of feedback.", feedbackItem0.hashCode(), feedbackItem1.hashCode());
    }
}
