package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class DataSourceTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        DataSource.countDataSources();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, DataSource.countDataSources());
    }

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
}
