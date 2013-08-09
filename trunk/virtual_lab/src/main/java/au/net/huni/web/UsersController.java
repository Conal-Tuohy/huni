package au.net.huni.web;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.net.huni.model.Researcher;

//Access by default is restricted to the ADMIN role within the console webapp.
//This is over-ridden by annotations in this file.
//See webmvc-config.xml
@RooWebJson(jsonObject = Researcher.class)
@Controller
public class UsersController {

	private final static HttpHeaders JSON_HEADERS = new HttpHeaders();
	static {
		JSON_HEADERS.add("Content-Type", "application/json; charset=utf-8");
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	// Allow access for VL web app.
	@PreAuthorize("isAnonymous()")
	@RequestMapping(value = "/rest/users/validate/{userName}/{password}", headers = "Accept=application/json", produces = "text/html")
	@ResponseBody
	public ResponseEntity<String> isValidUser(
			@PathVariable("userName") String userName,
			@PathVariable("password") String password,
			HttpServletRequest request) {

		Authentication authenticatedUser = null;
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
			token.setDetails(new WebAuthenticationDetails(request));
			authenticatedUser = authenticationManager.authenticate(token);
			Researcher researcher = null;
			if (authenticatedUser.isAuthenticated() && (researcher = findResearcher(userName)) != null) {
				return responsePacket(researcher.toJson(), HttpStatus.OK);
			}
		} catch (LockedException lockedException) {
			return responsePacket("{\"status\":\"failure\", \"reason\": \"Locked account\"}", HttpStatus.OK);
		} catch (BadCredentialsException badCredentialsException) {
			return responsePacket("{\"status\":\"failure\", \"reason\": \"Bad credentials\"}", HttpStatus.OK);
		} catch (EmptyResultDataAccessException emptyResultException) {
			return responsePacket(HttpStatus.NOT_FOUND);
		} catch (Exception exception) {
			return responsePacket(HttpStatus.NOT_FOUND);
		}
		return responsePacket(HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<String> responsePacket(HttpStatus status) {
		return new ResponseEntity<String>(JSON_HEADERS, status);
	}

	private ResponseEntity<String> responsePacket(String payload, HttpStatus status) {
		return new ResponseEntity<String>(payload, JSON_HEADERS, status);
	}

	// Used to stub out static method call for tests.
	protected Researcher findResearcher(String userName) {
		TypedQuery<Researcher> researcherQuery = Researcher.findResearchersByUserNameEquals(userName);
		return researcherQuery.getSingleResult();
	}

	@RequestMapping(value = "/rest/users/profile", method = RequestMethod.PUT, headers = "Accept=application/json", produces = "application/json")
	public ResponseEntity<String> editProfile(@RequestBody String json) {
		Researcher researcher = Researcher.fromJsonToResearcher(json);
		if (researcher.merge() == null) {
			return responsePacket(HttpStatus.NOT_FOUND);
		}
		return responsePacket(HttpStatus.OK);
	}

	// Inject service for tests.
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	//====================================================
	// Stubbed out to keep Roo from interfering.
	
	public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
		return null;
	}

	public ResponseEntity<String> listJson() {
		return null;
	}

	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		return null;
	}

	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		return null;
	}

	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		return null;
	}

	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		return null;
	}

	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
		return null;
	}

	public ResponseEntity<String> jsonFindResearchersByUserNameEquals(
			@RequestParam("userName") String userName) {
		return null;
	}
}
