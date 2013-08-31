package au.net.huni.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ErrorResponseTest {
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	ErrorResponse errorResponse = new ErrorResponse("failure2", "reason2");
    	
    	String actualJson = errorResponse.toJson();
    	
    	assertNotNull(actualJson);
    	assertTrue("JSON status is correct", actualJson.contains("\"status\":\"failure2\""));
    	assertTrue("JSON reason is correct", actualJson.contains("\"reason\":\"reason2\""));
     }

}
