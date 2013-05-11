package cz.cvut.fel.bupro.security;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.service.UserService;

@Service
public class SpringSecurityService implements SecurityService {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;

	private User find(Authentication authentication) {
		Object o = authentication.getPrincipal();
		if (o instanceof User) {
			return userService.getUser(((User) o).getId());
		}
		if (o instanceof UserDetails) {
			log.trace("Principal type UserDetails");
			UserDetails userDetails = (UserDetails) o;
			return userService.getUserByUsername(userDetails.getUsername());
		}
		if (o instanceof String) {
			log.trace("Principal type plain username");
			return userService.getUserByUsername(String.valueOf(o));
		}
		throw new IllegalStateException("Unknown type of principal " + o.getClass());
	}

	@Transactional
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			log.info("Authentication information not available!");
			return null;
		}
		User user = find(authentication);
		if (user == null) {
			log.info("User instance not found!");
		} else {
			user.getRoles().size(); //force fetch
		}
		return user;
	}

	@Transactional
	public User createUser(User user, Locale locale) {
		return userService.createNewUserAccount(user.getUsername(), locale);
	}

	public void changePassword(User user, String password) {
		userService.changePassword(user, password);
	}

	public void resetPassword(User user, Locale locale) {
		userService.resetPassword(user, locale);
	}

	public boolean checkPassword(User user, String password) {
		return userService.checkPassword(user, password);
	}
}
