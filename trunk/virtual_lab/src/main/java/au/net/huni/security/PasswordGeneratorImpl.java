package au.net.huni.security;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PasswordGeneratorImpl implements PasswordGenerator {
	
	// Ambiguous letters removed.
	private final static String CHARACTERS = 
			"abcdefghijkmnopqrstuvwxyz"
			+ "ABCDEFGHJKLMNPQRSTUVWXYZ"
			+ "123456789"
			+ "!@#$%^&*()_+<>?";
	
	private final static int CHARACTER_COUNT = 8;
	
	private Random rnd = new SecureRandom();

	@Override
	public String generate() {
		
		StringBuffer buffer = new StringBuffer(CHARACTER_COUNT);
		final int selectorRange = CHARACTERS.length();
		for (int characterIndex = 0; characterIndex < CHARACTER_COUNT; characterIndex++) {
			int selecterIndex = rnd.nextInt(selectorRange);
			String character = CHARACTERS.substring(selecterIndex, selecterIndex + 1);
			buffer.append(character);
		}
		return buffer.toString();
	}

}
