package au.net.huni.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class ToolCategoryTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        ToolCategory.countToolCategorys();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, ToolCategory.countToolCategorys());
    }

    @Test
    public void testToString() {
    	ToolCategory toolCategory = new ToolCategory();
    	toolCategory.setName("toolcategory0");
    	assertEquals("Tool category toString is name.", "toolcategory0", toolCategory.toString());
    }

    @Test
    public void testEquals() {
    	ToolCategory toolCategory0 = new ToolCategory();
    	toolCategory0.setName("toolCategory name0");
    	
    	ToolCategory toolCategory1 = new ToolCategory();
    	toolCategory1.setName("toolCategory name0");
    	
    	assertTrue("ToolCategory equals is based on name.", toolCategory0.equals(toolCategory1));
    }

    @Test
    public void testHashCode() {
    	ToolCategory toolCategory0 = new ToolCategory();
    	toolCategory0.setName("toolCategory name0");
    	
    	ToolCategory toolCategory1 = new ToolCategory();
    	toolCategory1.setName("toolCategory name0");
    	
    	assertEquals("ToolCategory hashcode is based on name.", toolCategory0.hashCode(), toolCategory1.hashCode());
    }
}
