package au.net.huni.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class HuniAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String acceptsHeader = request.getHeader("Accept");
		if ("application/json".equals(acceptsHeader)) {
			// Do nothing.
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}		
	}
}
