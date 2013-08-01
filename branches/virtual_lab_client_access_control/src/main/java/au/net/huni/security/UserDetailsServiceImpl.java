package au.net.huni.security;

import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.net.huni.model.Researcher;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		Researcher user = null;
		try {
			TypedQuery<Researcher> findResearchers = Researcher.findResearchersByUserNameEquals(username);
			user = findResearchers.getSingleResult();
		} catch (NoResultException noResult) {
			// Person not found.
			throw new UsernameNotFoundException("User not found");
		} catch (Exception e) {
			// For all other exceptions complain bitterly.
			e.printStackTrace();
		}

		// TODO RR: probably not needed as getSingleResult() throws an exception.
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		} else {
			// String name = user.getUserName();
			String password = user.getPassword();
			boolean enabled = user.getIsAccountEnabled();
			boolean accountNonExpired = user.getIsAccountEnabled();
			boolean credentialsNonExpired = user.getIsAccountEnabled();
			boolean accountNonLocked = user.getIsAccountEnabled();
			List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
			return new org.springframework.security.core.userdetails.User(
					username, password, enabled, accountNonExpired,
					credentialsNonExpired, accountNonLocked, authorities);
		}
	}
}