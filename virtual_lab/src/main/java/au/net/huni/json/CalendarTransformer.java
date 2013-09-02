package au.net.huni.json;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.transformer.AbstractTransformer;

public class CalendarTransformer extends AbstractTransformer implements
		ObjectFactory {
	
    static final Logger logger = Logger.getLogger(CalendarTransformer.class);

    private List<SimpleDateFormat> formatters = new ArrayList<SimpleDateFormat>();	 
    // 2013-09-16T05:26:47.009Z
			
	public CalendarTransformer(String[] dataTimePatterns) {
		super();
		for (String dataTimePattern : dataTimePatterns) {
			this.formatters.add(new SimpleDateFormat(dataTimePattern));
		}
	}

	@Override
	public void transform(Object object) {
		Calendar calendar = (Calendar)object;
		Date date = calendar.getTime();
		String formattedDateTime = formatters.get(0).format(date);
        this.getContext().writeQuoted(formattedDateTime);
	}

	@Override
	public Object instantiate(ObjectBinder context, Object value,
			Type targetType, @SuppressWarnings("rawtypes") Class targetClass) {
	    Calendar calendar = Calendar.getInstance();
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
	    calendar.setTimeZone(timeZone);
	    for (SimpleDateFormat formatter : formatters) {
			try {
			    Date date = formatter.parse((String) value);
			    calendar.setTime(date);
			    break;
			} catch (ParseException tryAgain) {
				logger.error("Failed to parse a JSON date with pattern. " + formatter, tryAgain);
			}
	    }
		return calendar;
	}
}
