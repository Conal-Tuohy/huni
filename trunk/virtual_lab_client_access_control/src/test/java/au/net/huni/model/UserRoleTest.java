package au.net.huni.model;

import static org.junit.Assert.*;

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

}
