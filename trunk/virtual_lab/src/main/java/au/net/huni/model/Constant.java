package au.net.huni.model;

import java.text.SimpleDateFormat;


public class Constant {
	public final static String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss z";
	//2013-09-16T05:26:47.009Z
	public final static String DATE_TIME_PATTERN_ALTERNATE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public final static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_TIME_PATTERN);
}
