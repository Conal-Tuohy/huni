package au.net.huni.model;

import static org.junit.Assert.assertEquals;
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
		institution.setVersion(20);
		institution.setId(10L);
    	
    	String actualJson = institution.toJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"Monash\""));
    	assertTrue("JSON has version", actualJson.contains("\"version\":20"));
    }

    @Test
    public void testToString() {
    	Institution institution = new Institution();
    	institution.setCode("code0");
    	institution.setName("institution name0");
    	assertEquals("Institution toString is code and name.", "code0 (institution name0)", institution.toString());
    }

    @Test
    public void testEquals() {
    	Institution institution0 = new Institution();
    	institution0.setCode("feedabackitem0");
    	institution0.setName("institution name0");
    	
    	Institution institution1 = new Institution();
    	institution1.setCode("feedabackitem0");
    	institution1.setName("institution name0");
    	
    	assertTrue("Institution equals is based on code.", institution0.equals(institution1));
    }

    @Test
    public void testHashCode() {
    	Institution institution0 = new Institution();
    	institution0.setCode("feedabackitem0");
    	institution0.setName("institution name0");
    	
    	Institution institution1 = new Institution();
    	institution1.setCode("feedabackitem0");
    	institution1.setName("institution name0");
    	
    	assertEquals("Institution hashcode is based on code.", institution0.hashCode(), institution1.hashCode());
    }
}
