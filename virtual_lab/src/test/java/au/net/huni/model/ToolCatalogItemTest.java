package au.net.huni.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class ToolCatalogItemTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        ToolCatalogItem.countToolCatalogItems();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, ToolCatalogItem.countToolCatalogItems());
    }

    @Test
    public void testToString() {
    	ToolCatalogItem toolCatalogItem = new ToolCatalogItem();
    	toolCatalogItem.setName("toolcatalog0");
    	assertEquals("Tool category toString is name.", "toolcatalog0", toolCatalogItem.toString());
    }
}
