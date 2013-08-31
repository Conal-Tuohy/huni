package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ToolLibraryItemTest {

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
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	ToolLibraryItem toolLibraryItem = new ToolLibraryItem();
    	toolLibraryItem.setId(25L);
    	toolLibraryItem.setVersion(30);
    	toolLibraryItem.setName("toolname0");
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );
		toolLibraryItem.setCreationDate(calendar);
    	toolLibraryItem.setSoftwareVersion("1.1.1");
		toolLibraryItem.setAuthor("Amerigo Verspucci");
		toolLibraryItem.setDescription("A wonderful tool");
		toolLibraryItem.setThumbnailFileName("defult_thumb.png");
		toolLibraryItem.setUrl("/tool-urn.xml");
    	
    	String actualJson = toolLibraryItem.toJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"toolname0\""));
     	assertTrue("JSON creation date is correct", actualJson.contains("\"creationDate\":\"25/12/2013 18:30:45 EST"));
    	assertTrue("JSON software version is correct", actualJson.contains("\"softwareVersion\":\"1.1.1\""));
    	assertTrue("JSON author is correct", actualJson.contains("\"author\":\"Amerigo Verspucci\""));
    	assertTrue("JSON description is correct", actualJson.contains("\"description\":\"A wonderful tool\""));
    	assertTrue("JSON thumbnail is correct", actualJson.contains("\"thumbnailFileName\":\"defult_thumb.png\""));
    	assertTrue("JSON thumbnail url is correct", actualJson.contains("\"thumbnailUrl\":\"/virtual_lab/img/tool-library/defult_thumb.png\""));
    	assertTrue("JSON url is correct", actualJson.contains("\"url\":\"/tool-urn.xml\""));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));
    }
    
    @Test 
    public void testToDeepJsonProducesCorrectJson() {
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );
		
		ToolCategory toolCategory = new ToolCategory();
		toolCategory.setId(106789L);
		toolCategory.setVersion(101234);
		toolCategory.setName("toolcategory0");
		Set<ToolCategory> categories = new HashSet<ToolCategory>();
		categories.add(toolCategory);

		ToolLibraryItem toolLibraryItem = new ToolLibraryItem();
    	toolLibraryItem.setId(25L);
    	toolLibraryItem.setVersion(30);
    	toolLibraryItem.setName("toolname0");
		toolLibraryItem.setCreationDate(calendar);
    	toolLibraryItem.setSoftwareVersion("1.1.1");
		toolLibraryItem.setAuthor("Amerigo Verspucci");
		toolLibraryItem.setDescription("A wonderful tool");
		toolLibraryItem.setThumbnailFileName("defult_thumb.png");
		toolLibraryItem.setUrl("/tool-urn.xml");
		toolLibraryItem.setCategories(categories);
    	
    	String actualJson = toolLibraryItem.toDeepJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"toolname0\""));
     	assertTrue("JSON creation date is correct", actualJson.contains("\"creationDate\":\"25/12/2013 18:30:45 EST"));
    	assertTrue("JSON software version is correct", actualJson.contains("\"softwareVersion\":\"1.1.1\""));
    	assertTrue("JSON author is correct", actualJson.contains("\"author\":\"Amerigo Verspucci\""));
    	assertTrue("JSON description is correct", actualJson.contains("\"description\":\"A wonderful tool\""));
    	assertTrue("JSON thumbnail is correct", actualJson.contains("\"thumbnailFileName\":\"defult_thumb.png\""));
    	assertTrue("JSON thumbnail url is correct", actualJson.contains("\"thumbnailUrl\":\"/virtual_lab/img/tool-library/defult_thumb.png\""));
    	assertTrue("JSON url is correct", actualJson.contains("\"url\":\"/tool-urn.xml\""));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));

    	assertFalse("JSON tool category id is not present", actualJson.contains("\"id\":106789"));
    	assertFalse("JSON tool category version not preseent", actualJson.contains("\"version\":101234"));
    	assertFalse("JSON tool category name property is not present", actualJson.contains("\"name\":\"toolcategory0\""));
    	assertTrue("JSON tool category name is present", actualJson.contains("\"toolcategory0\""));
    }
    
    @Test 
    public void testToDeepJsonArrayProducesCorrectJson() {
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );
		
		ToolCategory toolCategory = new ToolCategory();
		toolCategory.setId(106789L);
		toolCategory.setVersion(101234);
		toolCategory.setName("toolcategory0");
		Set<ToolCategory> categories = new HashSet<ToolCategory>();
		categories.add(toolCategory);

		ToolLibraryItem toolLibraryItem = new ToolLibraryItem();
    	toolLibraryItem.setId(25L);
    	toolLibraryItem.setVersion(30);
    	toolLibraryItem.setName("toolname0");
		toolLibraryItem.setCreationDate(calendar);
    	toolLibraryItem.setSoftwareVersion("1.1.1");
		toolLibraryItem.setAuthor("Amerigo Verspucci");
		toolLibraryItem.setDescription("A wonderful tool");
		toolLibraryItem.setThumbnailFileName("defult_thumb.png");
		toolLibraryItem.setUrl("/tool-urn.xml");
		toolLibraryItem.setCategories(categories);
		Set<ToolLibraryItem> toolkit = new HashSet<ToolLibraryItem>();
		toolkit.add(toolLibraryItem);
    	
    	String actualJson = ToolLibraryItem.toDeepJsonArray(toolkit);
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"toolname0\""));
     	assertTrue("JSON creation date is correct", actualJson.contains("\"creationDate\":\"25/12/2013 18:30:45 EST"));
    	assertTrue("JSON software version is correct", actualJson.contains("\"softwareVersion\":\"1.1.1\""));
    	assertTrue("JSON author is correct", actualJson.contains("\"author\":\"Amerigo Verspucci\""));
    	assertTrue("JSON description is correct", actualJson.contains("\"description\":\"A wonderful tool\""));
    	assertTrue("JSON thumbnail is correct", actualJson.contains("\"thumbnailFileName\":\"defult_thumb.png\""));
    	assertTrue("JSON thumbnail url is correct", actualJson.contains("\"thumbnailUrl\":\"/virtual_lab/img/tool-library/defult_thumb.png\""));
    	assertTrue("JSON url is correct", actualJson.contains("\"url\":\"/tool-urn.xml\""));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));

    	assertFalse("JSON tool category id is not present", actualJson.contains("\"id\":106789"));
    	assertFalse("JSON tool category version not preseent", actualJson.contains("\"version\":101234"));
    	assertFalse("JSON tool category name property is not present", actualJson.contains("\"name\":\"toolcategory0\""));
    	assertTrue("JSON tool category name is present", actualJson.contains("\"toolcategory0\""));
    }
}
