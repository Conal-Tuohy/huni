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
public class ProjectTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        Project.countProjects();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, Project.countProjects());
    }

    @Test
    public void testToString() {
    	Project project = new Project();
    	project.setName("project0");
    	assertEquals("Project toString is name.", "project0", project.toString());
    }

    @Test
    public void testEquals() {
    	Calendar startDate = Calendar.getInstance();
    	startDate.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	startDate.setTimeZone(timeZone);

    	Project project0 = new Project();
    	project0.setName("project name0");
    	project0.setStartDate(startDate);
    	
    	Project project1 = new Project();
    	project1.setName("project name0");
    	project1.setStartDate(startDate);
    	
    	assertTrue("Project equals is based on name and start date.", project0.equals(project1));
    }

    @Test
    public void testHashCode() {
    	Calendar startDate = Calendar.getInstance();
    	startDate.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	startDate.setTimeZone(timeZone);

    	Project project0 = new Project();
    	project0.setName("project name0");
    	project0.setStartDate(startDate);
    	
    	Project project1 = new Project();
    	project1.setName("project name0");
    	project1.setStartDate(startDate);
    	
    	assertEquals("Project hashcode is based on name and start date.", project0.hashCode(), project1.hashCode());
    }
}
