package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    	toolParameter.setId(24L);
    	toolParameter.setVersion(33);
    	toolParameter.setName("toolparameter1");
    	toolParameter.setAmount("amount1");
		toolParameter.setDisplayOrder(3);
    	
    	String actualJson = toolParameter.toJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"toolparameter1\""));
    	assertTrue("JSON amount is correct", actualJson.contains("\"amount\":\"amount1\""));
    	assertTrue("JSON displayOrder is correct", actualJson.contains("\"displayOrder\":3"));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":33"));
    }

    @Test
    public void testToString() {
    	ToolParameter toolParameter = new ToolParameter();
    	toolParameter.setName("toolcategory0");
    	assertEquals("Tool category toString is name.", "toolcategory0", toolParameter.toString());
    }

    @Test
    public void testEquals() {
    	ToolParameter toolParameter0 = new ToolParameter();
    	toolParameter0.setName("toolParameter name0");
    	toolParameter0.setAmount("234");
    	toolParameter0.setDisplayOrder(1);
    	
    	ToolParameter toolParameter1 = new ToolParameter();
    	toolParameter1.setName("toolParameter name0");
    	toolParameter1.setAmount("234");
    	toolParameter1.setDisplayOrder(1);
    	
    	assertTrue("ToolParameter equals is based on name.", toolParameter0.equals(toolParameter1));
    }

    @Test
    public void testHashCode() {
       	ToolParameter toolParameter0 = new ToolParameter();
    	toolParameter0.setName("toolParameter name0");
    	toolParameter0.setAmount("234");
    	toolParameter0.setDisplayOrder(1);
    	
    	ToolParameter toolParameter1 = new ToolParameter();
    	toolParameter1.setName("toolParameter name0");
    	toolParameter1.setAmount("234");
    	toolParameter1.setDisplayOrder(1);
    	
    	assertEquals("ToolParameter hashcode is based on name.", toolParameter0.hashCode(), toolParameter1.hashCode());
    }
}
