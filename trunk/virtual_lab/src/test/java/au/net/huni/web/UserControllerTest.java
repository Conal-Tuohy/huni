package au.net.huni.web;

import static org.junit.Assert.assertTrue;
import static org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.*;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import au.net.huni.model.Researcher;

@MockStaticEntityMethods

public class UserControllerTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	
	@Test
	public void testValidUserCredentialsProduceUserProfile() {
		final Researcher researcher = new Researcher();
		Long id = 25L;
		researcher.setId(id);
		String password = "dirty-little-secret";
		researcher.setPassword(password);
		researcher.setUserName("user1");
		researcher.setGivenName("given1");
		researcher.setFamilyName("family1");
		researcher.setCreationDate(Calendar.getInstance());
		
		final Authentication authenticatedUser = context.mock(Authentication.class);
		context.checking(new Expectations() {{
		    oneOf (authenticatedUser).isAuthenticated(); will(returnValue(true));
		}});
		
		final AuthenticationManager authenticationManager = context.mock(AuthenticationManager.class);
		context.checking(new Expectations() {{
		    oneOf (authenticationManager).authenticate(with(any(UsernamePasswordAuthenticationToken.class))); will(returnValue(authenticatedUser));
		}});

		final HttpServletRequest httpServletRequest = context.mock(HttpServletRequest.class);
		context.checking(new Expectations() {{
			ignoring(httpServletRequest);
		}});		
		
//		final EntityManager entityManager = context.mock(EntityManager.class);
//		context.checking(new Expectations() {{
//		}});		

		// Mock Roo ActiveRecord methods	
//        Researcher.findResearchersByUserNameEquals("user1");
//        expectReturn(researcherQuery);
//		Researcher.entityManager();
//        expectReturn(entityManager);
//        playback();
        
		UsersController controller = new UsersController() {
			protected Researcher findResearcher(String userName) {
				return researcher;
			}
		};
		controller.setAuthenticationManager(authenticationManager);
		ResponseEntity<String> response = controller.isValidUser("user1", password, httpServletRequest);
		assertTrue(response.toString().contains("user1"));
	}

}
