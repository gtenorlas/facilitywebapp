package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//import ca.sheridancollege.DAO.DAO;
import ca.sheridancollege.DAO.UserDAO;
/**
 * 
 * @author MAGS
 *
 */
public class MyUserDetailsService implements UserDetailsService {
 
	/**
	 * work with user details
	 */
	UserDAO userDAO = new UserDAO();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ca.sheridancollege.beans.User user = userDAO.findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
		return buildUserForAuthentication(user, authorities);

	}
	
	/**
	 * Converts ca.sheridancollege.beans.User user to org.springframework.security.core.userdetails.User
	 * @param user
	 * @param authorities
	 * @return user
	 */
	private User buildUserForAuthentication(ca.sheridancollege.beans.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
	
	/**
	 * build user authority
	 * @param userRoles
	 * @return granted user authority 
	 */
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}

}
