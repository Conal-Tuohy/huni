package au.net.huni.web;

import au.net.huni.model.Researcher;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Long, Researcher> getIdToResearcherConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.Researcher>() {
            public au.net.huni.model.Researcher convert(java.lang.Long id) {
                return Researcher.findResearcher(id);
            }
        };
    }

	public Converter<Researcher, String> getResearcherToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<au.net.huni.model.Researcher, java.lang.String>() {
            public String convert(Researcher researcher) {
                return new StringBuilder().append(researcher.getUserName()).append(" <").append(researcher.getGivenName()).append(' ').append(researcher.getFamilyName()).append('>').toString();
            }
        };
    }

	public Converter<String, Researcher> getStringToResearcherConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.Researcher>() {
            public au.net.huni.model.Researcher convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Researcher.class);
            }
        };
    }
}
