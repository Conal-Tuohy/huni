package au.net.huni.model;

import static org.junit.Assert.assertEquals;

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
}
