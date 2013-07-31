package au.net.huni.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import au.net.huni.model.Researcher;
import au.net.huni.test.DummyTypedQuery;

@RunWith(JUnit4.class)
@MockStaticEntityMethods

public class UserDetailsServiceImplTest {

	@Test
	public void testLoadUserByUsernameForKnownUser() {
		final String userName = "jbloggs";
		final String password = "secretsource";
        final String encryptedPassword = "6ab59a698e30b64dee5961eb75353bcd507355a0385fce2b551e371cd9abcbf0";
		final boolean isAccountEnabled = true;
		
		Researcher researcher = new Researcher();
		researcher.setUserName(userName);
		researcher.setPassword(password);
		researcher.setIsAccountEnabled(isAccountEnabled);
		final DummyTypedQuery<Researcher> expectedTypedQuery = new DummyTypedQuery<Researcher>(researcher);
	    	
		// Injection of behaviour into the entity class used within the loadUserByUsername call.
		Researcher.findResearchersByUserNameEquals(userName);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedTypedQuery);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        

        UserDetailsService service = new UserDetailsServiceImpl();
        UserDetails loadedUser = service.loadUserByUsername(userName);
        org.junit.Assert.assertEquals(userName, loadedUser.getUsername());
        org.junit.Assert.assertEquals(encryptedPassword, loadedUser.getPassword());
        org.junit.Assert.assertEquals(isAccountEnabled, loadedUser.isAccountNonExpired());
        org.junit.Assert.assertEquals(isAccountEnabled, loadedUser.isAccountNonLocked());
        org.junit.Assert.assertEquals(isAccountEnabled, loadedUser.isCredentialsNonExpired());
        org.junit.Assert.assertEquals("ROLE_USER", loadedUser.getAuthorities().iterator().next().getAuthority());
	}

	@Test (expected=UsernameNotFoundException.class)
	public void testLoadUserByUsernameForNullUser() {
		final String userName = "jbloggs";
		Researcher researcher = null;
		final DummyTypedQuery<Researcher> typedQuery = new DummyTypedQuery<Researcher>(researcher);
	    		
		Researcher.findResearchersByUserNameEquals(userName);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(typedQuery);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();

        UserDetailsService service = new UserDetailsServiceImpl();
        service.loadUserByUsername(userName);
	}

	@Test (expected=UsernameNotFoundException.class)
	public void testLoadUserByUsernameForUnknownUser() {
		final String userName = "jbloggs";
		boolean isThrowException = true;
		final DummyTypedQuery<Researcher> typedQuery = new DummyTypedQuery<Researcher>(isThrowException);
	    		
		Researcher.findResearchersByUserNameEquals(userName);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(typedQuery);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();

        UserDetailsService service = new UserDetailsServiceImpl();
        service.loadUserByUsername(userName);
	}
}
