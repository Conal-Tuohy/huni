package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    	userRole.setName("name1");
    	
    	String actualJson = userRole.toJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"name1\""));
    	assertFalse("JSON version is not present", actualJson.contains("\"version\":"));
    }

}
