package cz.cvut.fel.reposapi.assembla.client;

import org.springframework.http.HttpHeaders;

/**
 * Provides identification for accessing Assembla API
 * 
 * @author viktor
 * 
 */
public interface Identity {

	/**
	 * Set HTTP headers for Assembla request using this identity
	 * 
	 * @param httpHeaders
	 */
	public void authorize(HttpHeaders httpHeaders);
}
