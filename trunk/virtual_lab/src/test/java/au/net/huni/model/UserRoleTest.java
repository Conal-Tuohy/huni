package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserRoleTest {

	@Test
	public void testToStringWithNoRole() {
		UserRole userRole = new UserRole();
		assertEquals("", userRole.toString());
	}

	@Test
	public void testToStringWithNamedRole() {
		UserRole userRole = new UserRole("ADMIN");
		assertEquals("ADMIN", userRole.toString());
	}
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	UserRole userRole = new UserRole();
    	userRole.setVersion(33);
    	userRole.setName("name1");
    	
    	String actualJson = userRole.toJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"name1\""));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":33"));
    }

    @Test
    public void testEquals() {
    	UserRole userRole0 = new UserRole();
    	userRole0.setName("userRole name0");
    	
    	UserRole userRole1 = new UserRole();
    	userRole1.setName("userRole name0");
    	
    	assertTrue("UserRole equals is based on name.", userRole0.equals(userRole1));
    }

    @Test
    public void testHashCode() {
       	UserRole userRole0 = new UserRole();
    	userRole0.setName("userRole name0");
    	
    	UserRole userRole1 = new UserRole();
    	userRole1.setName("userRole name0");
    	
    	assertEquals("UserRole hashcode is based on name.", userRole0.hashCode(), userRole1.hashCode());
    }
}
