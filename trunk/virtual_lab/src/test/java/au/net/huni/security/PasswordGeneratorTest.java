package au.net.huni.security;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordGeneratorTest {

	@Test
	public void testGenerate() {
		PasswordGenerator generator = new PasswordGeneratorImpl();
		String password = generator.generate();
		assertEquals("Password generated", 8, password.length());
	}

}
