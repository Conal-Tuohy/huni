package au.net.huni.web;

import static org.junit.Assert.*;

import org.junit.Test;

import org.springframework.core.convert.converter.Converter;

import au.net.huni.model.Researcher;

public class ApplicationConversionServiceFactoryBeanTest {

	@Test
	public void testResearchConverter() {
		ApplicationConversionServiceFactoryBean converterFactory = new ApplicationConversionServiceFactoryBean();
		Converter<au.net.huni.model.Researcher, java.lang.String> converter = converterFactory.getResearcherToStringConverter();
		Researcher researcher = new Researcher();
		converter.convert(researcher );
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
		converter.convert(researcher );
		researcher.setUserName("jdoe");
		researcher.setFamilyName("Doe");
		researcher.setGivenName("Jane");
		researcher.setPassword("dirty-little-secret");
		assertTrue(converter.convert(researcher).length() < 64);
		assertFalse(converter.convert(researcher).contains("dirty-little-secret"));
	}

}
