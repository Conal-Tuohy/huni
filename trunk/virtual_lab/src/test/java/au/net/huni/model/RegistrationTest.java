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
public class RegistrationTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        Registration.countRegistrations();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, Registration.countRegistrations());
    }
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );

		Registration registration = new Registration();
		registration.setVersion(60);
    	registration.setUserName("jbloggs");
    	registration.setGivenName("Joseph");
    	registration.setFamilyName("Bloggs");
		registration.setApplicationDate(calendar);
		registration.setApprovalDate(calendar);
		registration.setEmailAddress("jblogs@ordinary.com");
		
		Institution institution = new Institution("MONASH", "Monash");
		institution.setId(10L);
		institution.setVersion(30);
		registration.setInstitution(institution);
    	registration.setStatus(RegistrationStatus.PENDING);
    	
    	String actualJson = registration.toJson();
    	
    	assertTrue("JSON username is correct", actualJson.contains("\"userName\":\"jbloggs\""));
    	assertTrue("JSON given name is correct", actualJson.contains("\"givenName\":\"Joseph\""));
    	assertTrue("JSON family is correct", actualJson.contains("\"familyName\":\"Bloggs\""));
    	assertTrue("JSON institution is correct", actualJson.contains("\"institution\":{\"code\":\"MONASH\",\"id\":10,\"name\":\"Monash\",\"version\":30}"));
    	assertTrue("JSON application date is correct", actualJson.contains("\"applicationDate\":\"25/12/2013 18:30:45 EST\""));
    	assertTrue("JSON approval date is correct", actualJson.contains("\"approvalDate\":\"25/12/2013 18:30:45 EST\""));
    	assertTrue("JSON email is correct", actualJson.contains("\"emailAddress\":\"jblogs@ordinary.com\""));
    	assertTrue("JSON status is correct", actualJson.contains("\"status\":\"PENDING\""));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));
    }
  
 	@Test
 	public void testToStringIncludesUserNameEtc() {
 		Registration registration = new Registration();
 		registration.setUserName("jdoe");
 		registration.setFamilyName("Doe");
 		registration.setGivenName("Jane");
 		assertEquals("Doe, Jane (jdoe)", registration.toString());
 	}

    @Test
    public void testEquals() {
    	Calendar applicationDate = Calendar.getInstance();
    	applicationDate.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	applicationDate.setTimeZone(timeZone);

    	Registration registration0 = new Registration();
    	registration0.setUserName("registration name0");
    	registration0.setApplicationDate(applicationDate);
    	
    	Registration registration1 = new Registration();
    	registration1.setUserName("registration name0");
    	registration1.setApplicationDate(applicationDate);
    	
    	assertTrue("Registration equals is based on user name and start date.", registration0.equals(registration1));
    }

    @Test
    public void testHashCode() {
    	Calendar applicationDate = Calendar.getInstance();
    	applicationDate.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	applicationDate.setTimeZone(timeZone);

    	Registration registration0 = new Registration();
    	registration0.setUserName("registration name0");
    	registration0.setApplicationDate(applicationDate);
    	
    	Registration registration1 = new Registration();
    	registration1.setUserName("registration name0");
    	registration1.setApplicationDate(applicationDate);
    	
    	assertEquals("Registration hashcode is based on user name and start date.", registration0.hashCode(), registration1.hashCode());
    }
}
