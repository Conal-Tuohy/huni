package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.TypedQuery;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

import au.net.huni.test.DummyTypedQuery;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class ResearcherTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        Researcher.countResearchers();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, Researcher.countResearchers());
    }

	@Test
	public void testLoadUserByUsernameForKnownUser() {
		final String userName = "jbloggs";
		Researcher researcher = new Researcher();
		final DummyTypedQuery<Researcher> expectedTypedQuery = new DummyTypedQuery<Researcher>(researcher);
	    		
		Researcher.findResearchersByUserNameEquals(userName);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedTypedQuery);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        
        TypedQuery<Researcher> typedQuery = Researcher.findResearchersByUserNameEquals(userName);
        org.junit.Assert.assertEquals(expectedTypedQuery, typedQuery);
	}
    
    @Test 
    public void testToJsonExcludesPasswords() {
    	Researcher researcher = new Researcher();
    	// Set to keep hashcode happy.
    	researcher.setUserName("user1");
    	// Set to keep hashcode happy.
    	researcher.setCreationDate(Calendar.getInstance());
    	researcher.setPassword("dirty-little-secret");
    	assertFalse(researcher.toJson().contains("password"));
    	assertFalse(researcher.toJson().contains("encryptedPassword"));   	
    }
    
    @Ignore
    // This test run foul of the static method on Researcher not having its entity manager set.
    // I can inject a mock one but this seems to cause other problems.
    public void testToJsonArrayExcludesPasswords() {
    	Researcher researcher = new Researcher();
    	researcher.setPassword("dirty-little-secret");
    	List <Researcher> researchers = Arrays.asList(researcher);
    	assertFalse(Researcher.toJsonArray(researchers).contains("password"));
    	assertFalse(Researcher.toJsonArray(researchers).contains("encryptedPassword"));  	
    }
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	Researcher researcher = new Researcher();
    	researcher.setId(25L);
    	researcher.setVersion(30);
    	researcher.setUserName("jbloggs");
    	researcher.setGivenName("Joseph");
    	researcher.setFamilyName("Bloggs");
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );
		researcher.setCreationDate(calendar);
		researcher.setEmailAddress("jblogs@ordinary.com");
		Institution institution = new Institution("MONASH", "Monash");
		researcher.setInstitution(institution);
    	researcher.setPassword("dirty-little-secret");
    	researcher.setIsAccountEnabled(true);
		institution.setId(10L);
		institution.setVersion(20);
    	
    	// TODO RR Add roles
    	
    	String actualJson = researcher.toJson();
    	
    	assertTrue("JSON username is correct", actualJson.contains("\"userName\":\"jbloggs\""));
    	assertTrue("JSON given name is correct", actualJson.contains("\"givenName\":\"Joseph\""));
    	assertTrue("JSON family is correct", actualJson.contains("\"familyName\":\"Bloggs\""));
    	assertTrue("JSON institution is correct", actualJson.contains("\"institution\":{\"code\":\"MONASH\",\"id\":10,\"name\":\"Monash\",\"version\":20}"));
    	assertTrue("JSON creation date is correct", actualJson.contains("\"creationDate\":\"25/12/2013 18:30:45 EST"));
    	assertTrue("JSON email is correct", actualJson.contains("\"emailAddress\":\"jblogs@ordinary.com\""));
    	assertTrue("JSON enable account is correct", actualJson.contains("\"isAccountEnabled\":true"));
    	assertTrue("JSON version for institution is present", actualJson.contains("\"version\":20"));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));
    }
    
	@Test
	public void testToStringIncludesUserNameEtc() {
		Researcher researcher = new Researcher();
		researcher.setUserName("jdoe");
		researcher.setFamilyName("Doe");
		researcher.setGivenName("Jane");
		researcher.setPassword("dirty-little-secret");
		assertEquals("Doe, Jane (jdoe)", researcher.toString());
	}

	@Test
	public void testToStringExcludesPasswords() {
		Researcher researcher = new Researcher();
		researcher.setPassword("dirty-little-secret");
		assertTrue(researcher.toString().length() < 64);
		assertFalse(researcher.toString().contains("dirty-little-secret"));
	}

    @Test
    public void testEquals() {
    	Calendar creationDate = Calendar.getInstance();
    	creationDate.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	creationDate.setTimeZone(timeZone);

    	Researcher researcher0 = new Researcher();
    	researcher0.setUserName("researcher name0");
    	researcher0.setCreationDate(creationDate);
    	
    	Researcher researcher1 = new Researcher();
    	researcher1.setUserName("researcher name0");
    	researcher1.setCreationDate(creationDate);
    	
    	assertTrue("Researcher equals is based on user name and start date.", researcher0.equals(researcher1));
    }

    @Test
    public void testHashCode() {
    	Calendar creationDate = Calendar.getInstance();
    	creationDate.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	creationDate.setTimeZone(timeZone);

    	Researcher researcher0 = new Researcher();
    	researcher0.setUserName("researcher name0");
    	researcher0.setCreationDate(creationDate);
    	
    	Researcher researcher1 = new Researcher();
    	researcher1.setUserName("researcher name0");
    	researcher1.setCreationDate(creationDate);
    	
    	assertEquals("Researcher hashcode is based on user name and start date.", researcher0.hashCode(), researcher1.hashCode());
    }

}