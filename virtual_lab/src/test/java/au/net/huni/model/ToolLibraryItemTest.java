package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class ToolLibraryItemTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        ToolLibraryItem.countToolLibraryItems();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, ToolLibraryItem.countToolLibraryItems());
    }

    @Test
    public void testToString() {
    	ToolLibraryItem toolCatalogItem = new ToolLibraryItem();
    	toolCatalogItem.setName("toolcatalog0");
    	assertEquals("Tool category toString is name.", "toolcatalog0", toolCatalogItem.toString());
    }

    @Test
    public void testEquals() {
    	ToolLibraryItem toolCatalogItem0 = new ToolLibraryItem();
    	toolCatalogItem0.setUrl("/url0");
    	toolCatalogItem0.setName("toolCatalogItem name0");
    	
    	ToolLibraryItem toolCatalogItem1 = new ToolLibraryItem();
    	toolCatalogItem1.setUrl("/url0");
    	toolCatalogItem1.setName("toolCatalogItem name0");
    	
    	assertTrue("ToolCatalogItem equals is based on code.", toolCatalogItem0.equals(toolCatalogItem1));
    }

    @Test
    public void testHashCode() {
    	ToolLibraryItem toolCatalogItem0 = new ToolLibraryItem();
    	toolCatalogItem0.setUrl("/url0");
    	toolCatalogItem0.setName("toolCatalogItem name0");
    	
    	ToolLibraryItem toolCatalogItem1 = new ToolLibraryItem();
    	toolCatalogItem1.setUrl("/url0");
    	toolCatalogItem1.setName("toolCatalogItem name0");
    	
    	assertEquals("ToolCatalogItem hashcode is based on code.", toolCatalogItem0.hashCode(), toolCatalogItem1.hashCode());
    }
}
