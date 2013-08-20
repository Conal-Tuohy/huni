package au.net.huni.model;

import static org.junit.Assert.assertEquals;

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
    	project.setName("datasource0");
    	assertEquals("Data source toString is name.", "datasource0", project.toString());
    }
}
