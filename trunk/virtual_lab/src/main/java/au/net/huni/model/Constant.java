package au.net.huni.model;

import java.text.SimpleDateFormat;

import au.net.huni.json.CalendarTransformer;

public class Constant {
	public final static String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss z";
	public final static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_TIME_PATTERN);
	public static final CalendarTransformer CALENDAR_TRANSFORMER = new CalendarTransformer(DATE_TIME_PATTERN);
}
