package au.net.huni.web;

import static org.junit.Assert.assertEquals;
import static org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn;
import static org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import au.net.huni.model.Researcher;

@MockStaticEntityMethods
@Ignore 
// TODO RR: The test case works fine alone,
// but when run as part of the suite fails its expectations on the entity manager.
public class ResearcherControllerTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	
	@Test
	public void testCreateEncryptsPassword() {
		final Researcher researcher = new Researcher();
		Long id = 25L;
		researcher.setId(id);
		String password = "dirty-little-secret";
		researcher.setPassword(password);
		researcher.setUserName("user");
		researcher.setGivenName("given");
		researcher.setFamilyName("family");

		final BindingResult bindingResult = context.mock(BindingResult.class);
		context.checking(new Expectations() {{
		    oneOf (bindingResult).hasErrors(); will(returnValue(false));
		}});
		
		final Map<String, Object> modelMap = new HashMap<String, Object>();		
		final Model uiModel = context.mock(Model.class);
		context.checking(new Expectations() {{
		    oneOf (uiModel).asMap(); will(returnValue(modelMap));
		}});

		final HttpServletRequest httpServletRequest = context.mock(HttpServletRequest.class);
		context.checking(new Expectations() {{
		    oneOf (httpServletRequest).getCharacterEncoding(); will(returnValue("UTF-8"));
		}});
		
		final EntityManager entityManager = context.mock(EntityManager.class);
		context.checking(new Expectations() {{
		    oneOf (entityManager).persist(researcher); 
		}});

		// Mock Roo ActiveRecord methods
		
        Researcher.entityManager();
        expectReturn(entityManager);
        playback();

		// SHA-256 in Hex format accounts for the 64
        assertEquals(64, researcher.getPassword().length());
        
		ResearcherController controller = new ResearcherController();
		controller.create(researcher, bindingResult, uiModel, httpServletRequest);
		// SHA-256 in Hex format accounts for the 64
		assertEquals(64, researcher.getPassword().length());
	}

	@Test
	public void testUpdateModifiesAllExceptPassword() {

		final Researcher researcher = new Researcher();
		final Long id = 25L;
		researcher.setId(id);
		String password = "dirty-little-secret";
		researcher.setPassword(password);
		researcher.setUserName("user1");
		researcher.setGivenName("given1");
		researcher.setFamilyName("family1");

		final String savedPassword = "0000000000111111111122222222223333333333444444444455555555550000";
		final Researcher savedResearcher = new Researcher() {
			public String getPassword() {
		        return savedPassword;
		    }
		};
		Long savedId = 25L;
		savedResearcher.setId(savedId);
		savedResearcher.setUserName("user0");
		savedResearcher.setGivenName("given0");
		savedResearcher.setFamilyName("family0");

		final BindingResult bindingResult = context.mock(BindingResult.class);
		context.checking(new Expectations() {{
		    oneOf (bindingResult).hasErrors(); will(returnValue(false));
		}});

		final Map<String, Object> modelMap = new HashMap<String, Object>();		
		final Model uiModel = context.mock(Model.class);
		context.checking(new Expectations() {{
		    oneOf (uiModel).asMap(); will(returnValue(modelMap));
		}});

		final HttpServletRequest httpServletRequest = context.mock(HttpServletRequest.class);
		context.checking(new Expectations() {{
		    oneOf (httpServletRequest).getCharacterEncoding(); will(returnValue("UTF-8"));
		}});
		
		final EntityManager entityManager = context.mock(EntityManager.class);
		context.checking(new Expectations() {{
			Matcher<Object> hasOldPassword = Matchers.hasProperty("password", equal(savedPassword));
			Matcher<Object> hasNewUserName = Matchers.hasProperty("userName", equal("user1"));
			Matcher<Object> hasNewGivenName = Matchers.hasProperty("givenName", equal("given1"));
			Matcher<Object> hasNewFamilyName = Matchers.hasProperty("familyName", equal("family1"));
		    oneOf (entityManager).merge(with(Matchers.allOf(hasOldPassword, hasNewUserName, hasNewGivenName, hasNewFamilyName))); will(returnValue(savedResearcher));
		    oneOf (entityManager).flush(); 
		}});

		// Mock Roo ActiveRecord methods

		Researcher.findResearcher(researcher.getId());
        expectReturn(savedResearcher);

        Researcher.entityManager();
        expectReturn(entityManager);
        
        playback();

        ResearcherController controller = new ResearcherController();
		controller.update(researcher, bindingResult, uiModel, httpServletRequest);
		assertEquals("0000000000111111111122222222223333333333444444444455555555550000", savedResearcher.getPassword());
	}
}
