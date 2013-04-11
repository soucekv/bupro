package cz.cvut.fel.bupro.security;

import cz.cvut.fel.bupro.model.User;

public interface SecurityService {
	
	/**
	 * 
	 * @return domain specific instance of authenticated user from current request
	 */
	User getCurrentUser();
}
