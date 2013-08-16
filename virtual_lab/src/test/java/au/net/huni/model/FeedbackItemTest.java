package au.net.huni.model;

import static org.junit.Assert.assertFalse;
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
    	assertFalse("JSON version is not present", actualJson.contains("\"version\":"));
    }
}
