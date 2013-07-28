package au.net.huni.web;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.security.authentication.AuthenticationManager;
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

@RooWebJson(jsonObject = Researcher.class)
@Controller
public class UsersController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/rest/users/validate/{userName}/{password}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> isValidUser(@PathVariable("userName") String userName, @PathVariable("password") String password, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
		Authentication authenticatedUser = null;
		
		try{		
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
			token.setDetails(new WebAuthenticationDetails(request));
			authenticatedUser = authenticationManager.authenticate(token);
		} catch (Exception exception) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}

        if (authenticatedUser.isAuthenticated()) {
	        TypedQuery<Researcher> researcherQuery = Researcher.findResearchersByUserNameEquals(userName);
	        Researcher researcher = null;
	        try {
		        researcher = researcherQuery.getSingleResult();
			} catch (Exception exception) {
				if (exception instanceof EmptyResultDataAccessException) {
					researcher = null;
				}
			}
	        if (researcher == null) {
	            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<String>(researcher.toJson(), headers, HttpStatus.OK);
		}
		else{
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
    }

	@RequestMapping(value = "/rest/users/profile", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> editProfile(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Researcher researcher = Researcher.fromJsonToResearcher(json);
        if (researcher.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }


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

    public ResponseEntity<String> jsonFindResearchersByUserNameEquals(@RequestParam("userName") String userName) {
        return null;
    }
}
