package cz.cvut.fel.bupro.security;

import java.util.Locale;

import cz.cvut.fel.bupro.model.User;

public interface SecurityService {

	/**
	 * @return domain specific instance of authenticated user from current request
	 */
	User getCurrentUser();

	User createUser(User user, Locale locale);

	void changePassword(User user, String password);

	void resetPassword(User user, Locale locale);

	boolean checkPassword(User user, String password);
}
