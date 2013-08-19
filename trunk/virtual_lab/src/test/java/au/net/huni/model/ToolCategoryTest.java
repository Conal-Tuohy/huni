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
}
