package au.net.huni.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;


public class HuniAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		String acceptsHeader = request.getHeader("Accept");
		if (acceptsHeader != null && acceptsHeader.startsWith("application/json")) {
			// Return a JSON packet describing the failure.
			// Need to verify that the header is set correctly.
			// The web interface should display the login form.
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}		
	}
}
