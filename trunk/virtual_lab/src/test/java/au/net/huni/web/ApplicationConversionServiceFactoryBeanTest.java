package au.net.huni.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.core.convert.converter.Converter;

import au.net.huni.model.Researcher;
import au.net.huni.model.ToolCatalogItem;
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
	public void testToolCatalogItemConverter() {
		ApplicationConversionServiceFactoryBean converterFactory = new ApplicationConversionServiceFactoryBean();
		Converter<au.net.huni.model.ToolCatalogItem, java.lang.String> converter = converterFactory.getToolCatalogItemToStringConverter();
		ToolCatalogItem toolCatalogItem = new ToolCatalogItem();
		toolCatalogItem.setName("toolcatalogitem1");
		assertEquals("toolcatalogitem1", converter.convert(toolCatalogItem));
	}

}
