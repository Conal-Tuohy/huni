package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DataSourceTest {

    @Test
    public void testToString() {
    	DataSource dataSource = new DataSource();
    	dataSource.setName("datasource0");
    	assertEquals("Data source toString is name.", "datasource0", dataSource.toString());
    }

    @Test
    public void testEquals() {
    	Calendar today = Calendar.getInstance();
    	today.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	today.setTimeZone(timeZone);

    	DataSource dataSource0 = new DataSource();
    	dataSource0.setName("datasource0");
    	dataSource0.setImportDate(today);
    	
    	DataSource dataSource1 = new DataSource();
    	dataSource1.setName("datasource0");
    	dataSource1.setImportDate(today);
    	
    	assertTrue("Datasource equals is based on name and date of import.", dataSource0.equals(dataSource1));
    }

    @Test
    public void testHashCode() {
    	Calendar today = Calendar.getInstance();
    	today.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	today.setTimeZone(timeZone);

    	DataSource dataSource0 = new DataSource();
    	dataSource0.setName("datasource0");
    	dataSource0.setImportDate(today);
    	
    	DataSource dataSource1 = new DataSource();
    	dataSource1.setName("datasource0");
    	dataSource1.setImportDate(today);
    	
    	assertEquals("Datasource hashcode is based on name and date of import.", dataSource0.hashCode(), dataSource1.hashCode());
    }
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );

		DataSource dataSource = new DataSource();
    	dataSource.setId(25L);
    	dataSource.setVersion(30);
    	dataSource.setName("dataSource0");
		dataSource.setImportDate(calendar);
    	
    	String actualJson = dataSource.toJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"dataSource0\""));
     	assertTrue("JSON import date is correct", actualJson.contains("\"importDate\":\"25/12/2013 18:30:45 EST"));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));
    	assertTrue("JSON id is present", actualJson.contains("\"id\":25"));
    }
    
    @Test 
    public void testToJsonArrayProducesCorrectJson() {

    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");

    	DataSource dataSource = new DataSource();
    	dataSource.setId(25L);
    	dataSource.setVersion(30);
    	dataSource.setName("dataSource0");
		calendar.setTimeZone(timeZone );
		dataSource.setImportDate(calendar);
		
		Set<DataSource> dataSources = new HashSet<DataSource>();
		dataSources.add(dataSource);
    	
    	String actualJson = DataSource.toJsonArray(dataSources);
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"dataSource0\""));
     	assertTrue("JSON start date is correct", actualJson.contains("\"importDate\":\"25/12/2013 18:30:45 EST"));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));
    	assertTrue("JSON id is present", actualJson.contains("\"id\":25"));
    }
}
