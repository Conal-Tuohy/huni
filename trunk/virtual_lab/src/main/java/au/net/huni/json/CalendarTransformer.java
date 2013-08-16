package au.net.huni.json;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import au.net.huni.web.RegistrationController;

import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.transformer.AbstractTransformer;

public class CalendarTransformer extends AbstractTransformer implements
		ObjectFactory {
	
    static final Logger logger = Logger.getLogger(RegistrationController.class);

    private SimpleDateFormat formatter;	 	
			
	public CalendarTransformer(String dataTimePattern) {
		super();
		this.formatter = new SimpleDateFormat(dataTimePattern);
	}

	@Override
	public void transform(Object object) {
		Calendar calendar = (Calendar)object;
		Date date = calendar.getTime();
		String formattedDateTime = formatter.format(date);
        this.getContext().writeQuoted(formattedDateTime);
	}

	@Override
	public Object instantiate(ObjectBinder context, Object value,
			Type targetType, @SuppressWarnings("rawtypes") Class targetClass) {
		try {
			return formatter.parse((String) value);
		} catch (ParseException parseException) {
			logger.error("Failed to parse a JSON date. ", parseException);
			throw new RuntimeException("Failed to parse a JSON date. ", parseException);
		}
	}
}
