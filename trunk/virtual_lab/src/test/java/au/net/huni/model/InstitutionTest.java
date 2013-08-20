package au.net.huni.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class InstitutionTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        Institution.countInstitutions();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, Institution.countInstitutions());
    }
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	Institution institution = new Institution();
    	institution.setName("Monash");
		institution.setId(10L);
    	
    	String actualJson = institution.toJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"Monash\""));
    	assertFalse("JSON has no version", actualJson.contains("\"version\":10"));
    }
}
