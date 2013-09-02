package au.net.huni.web;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.ModelAndViewAssert.assertAndReturnModelAttributeOfType;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Exemplar for testing controllers. See:
 * http://www.finalconcept.com.au/article/view/spring-unit-testing-controllers
 * 
 * Some stuff not used, but leaving it in for future reference.
 * 
 * This test class breaks subsequent tests so it has been disabled.
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)

// webmvc-config-test.xml is the same as webmvc-config.xml,
// but the Apache Tiles bean has been commented out.
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/applicationContext.xml",
		"classpath:/META-INF/spring/applicationContext-security.xml",
		"classpath:/META-INF/spring-test/applicationContext-test.xml",
		"file:src/main/webapp/WEB-INF/spring/webmvc-config-test.xml" })

@DirtiesContext
@Ignore
public class RegistrationControllerIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private RequestMappingHandlerAdapter handlerAdapter;
	private RegistrationController controller;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		handlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);

		// I could get the controller from the context here
		// controller = new RegistrationController();
		controller = applicationContext.getBean(RegistrationController.class);
	}

	@Test
	public void testWiring() {
		assertNotNull("Mail template is wired", controller.getMailTemplate());
		assertNotNull("Password generator is wired", controller.getPasswordGenerator());
	}

	@Ignore
	public void testMethod() throws Exception {
		
		final String expectedMessage = "Hello jack, from the controller";
		final String requestUri = "/controller/test/jack";
		final String message;
		final Object handler;
		final HandlerMethod expectedHandlerMethod;
		final ModelAndView mav;
		final MockHttpServletRequest request;
		final MockHttpServletResponse response;

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		request.setRequestURI(requestUri);

		expectedHandlerMethod = new HandlerMethod(controller, "get", Model.class, String.class);
		handler = this.getHandler(request);
		// For the most part we will be expecting HandlerMethod objects to be
		// returned for our controllers.
		// Calling the to string method will print the complete method
		// signature.
		Assert.assertEquals("Correct handler found for request url: "
				+ requestUri, expectedHandlerMethod.toString(),
				handler.toString());

		// Handle the actual request
		mav = handlerAdapter.handle(request, response, handler);

		// Ensure that the right view is returned
		assertViewName(mav, "message-show");
		// Ensure that the view will receive the message object and that it is
		// a string
		message = assertAndReturnModelAttributeOfType(mav, "message", String.class);
		// We can test the message in case
		Assert.assertEquals(("Message returned was " + expectedMessage), expectedMessage, message);
	}

	/**
	 * This method finds the handler for a given request URI.
	 * 
	 * It will also ensure that the URI Parameters i.e. /context/test/{name} are
	 * added to the request
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private Object getHandler(MockHttpServletRequest request) throws Exception {
		HandlerExecutionChain chain = null;

		Map<String, HandlerMapping> map = applicationContext.getBeansOfType(HandlerMapping.class);
		Iterator<HandlerMapping> itt = map.values().iterator();

		while (itt.hasNext()) {
			HandlerMapping mapping = itt.next();
			chain = mapping.getHandler(request);
			if (chain != null) {
				break;
			}
		}

		if (chain == null) {
			throw new InvalidParameterException(
					"Unable to find handler for request URI: "
							+ request.getRequestURI());
		}
		return chain.getHandler();
	}
}
