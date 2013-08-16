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
public class ToolParameterTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        ToolParameter.countToolParameters();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, ToolParameter.countToolParameters());
    }
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	ToolParameter toolParameter = new ToolParameter();
    	toolParameter.setName("toolparameter1");
    	toolParameter.setAmount("amount1");
		toolParameter.setDisplayOrder(3);
    	
    	String actualJson = toolParameter.toJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"toolparameter1\""));
    	assertTrue("JSON amount is correct", actualJson.contains("\"amount\":\"amount1\""));
    	assertTrue("JSON displayOrder is correct", actualJson.contains("\"displayOrder\":3"));
    	//assertTrue("JSON owner is correct", actualJson.contains("\"owner\":\"jbloggs\""));
    	assertFalse("JSON version is not present", actualJson.contains("\"version\":"));
    }
}
