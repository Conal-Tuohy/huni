package au.net.huni.json;

import java.lang.reflect.Type;

import au.net.huni.model.Institution;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;

import static au.net.huni.model.Constant.*;

public class Transformer {

	public static final ObjectFactory INSTITUTION_OBJECT_FACTORY = new ObjectFactory() {
	
	    @SuppressWarnings("rawtypes")
	    @Override
	    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
	        Long id = Long.valueOf((String) value);
	        return Institution.findInstitution(id);
	    }
	};
	
	private static String[] DATE_PATTERNS = new String[]{
		DATE_TIME_PATTERN, 
		DATE_TIME_PATTERN_ALTERNATE};
	
	public static final CalendarTransformer CALENDAR_TRANSFORMER = new CalendarTransformer(DATE_PATTERNS);
	
	public static final CategoryTransformer CATEGORY_TRANSFORMER = new CategoryTransformer();

}
