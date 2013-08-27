package au.net.huni.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;
import org.springframework.core.convert.converter.Converter;

import au.net.huni.model.HistoryItem;
import au.net.huni.model.Researcher;
import au.net.huni.model.ToolLibraryItem;
import au.net.huni.model.ToolCategory;

public class ApplicationConversionServiceFactoryBeanTest {

	@Test
	public void testResearchConverter() {
		ApplicationConversionServiceFactoryBean converterFactory = new ApplicationConversionServiceFactoryBean();
		Converter<au.net.huni.model.Researcher, java.lang.String> converter = converterFactory.getResearcherToStringConverter();
		Researcher researcher = new Researcher();
		researcher.setUserName("jdoe");
		researcher.setFamilyName("Doe");
		researcher.setGivenName("Jane");
		researcher.setPassword("dirty-little-secret");
		assertEquals("jdoe <Jane Doe>", converter.convert(researcher));
	}

	@Test
	public void testResearchConverterExcludesPasswords() {
		ApplicationConversionServiceFactoryBean converterFactory = new ApplicationConversionServiceFactoryBean();
		Converter<au.net.huni.model.Researcher, java.lang.String> converter = converterFactory.getResearcherToStringConverter();
		Researcher researcher = new Researcher();
		researcher.setUserName("jdoe");
		researcher.setFamilyName("Doe");
		researcher.setGivenName("Jane");
		researcher.setPassword("dirty-little-secret");
		assertTrue(converter.convert(researcher).length() < 64);
		assertFalse(converter.convert(researcher).contains("dirty-little-secret"));
	}

	@Test
	public void testToolCategoryConverter() {
		ApplicationConversionServiceFactoryBean converterFactory = new ApplicationConversionServiceFactoryBean();
		Converter<au.net.huni.model.ToolCategory, java.lang.String> converter = converterFactory.getToolCategoryToStringConverter();
		ToolCategory toolCategory = new ToolCategory();
		toolCategory.setName("toolcategory1");
		assertEquals("toolcategory1", converter.convert(toolCategory));
	}

	@Test
	public void testToolLibraryItemConverter() {
		ApplicationConversionServiceFactoryBean converterFactory = new ApplicationConversionServiceFactoryBean();
		Converter<au.net.huni.model.ToolLibraryItem, java.lang.String> converter = converterFactory.getToolLibraryItemToStringConverter();
		ToolLibraryItem toolCatalogItem = new ToolLibraryItem();
		toolCatalogItem.setName("toolcatalogitem1");
		assertEquals("toolcatalogitem1", converter.convert(toolCatalogItem));
	}

	@Test
	public void testHistoryItemConverter() {
		ApplicationConversionServiceFactoryBean converterFactory = new ApplicationConversionServiceFactoryBean();
		Converter<au.net.huni.model.HistoryItem, String> converter = converterFactory.getHistoryItemToStringConverter();
		HistoryItem historyItem = new HistoryItem();
		historyItem.setToolName("tool1");
    	Calendar today = Calendar.getInstance();
    	today.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	today.setTimeZone(timeZone );
    	historyItem.setExecutionDate(today);
		assertEquals("tool1(25/12/2013 18:30:45 EST)", converter.convert(historyItem));
	}

}
