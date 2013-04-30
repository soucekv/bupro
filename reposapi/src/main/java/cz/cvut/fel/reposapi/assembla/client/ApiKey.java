package cz.cvut.fel.reposapi.assembla.client;

import org.springframework.http.HttpHeaders;

/**
 * Use API key and secret to generate API sessions for your user identity
 * 
 * @author viktor
 * 
 */
public class ApiKey implements Identity {

	private final String key;
	private final String secret;

	public ApiKey(String key, String secret) {
		this.key = key;
		this.secret = secret;
	}

	public void authorize(HttpHeaders httpHeaders) {
		httpHeaders.set("X-Api-key", key);
		httpHeaders.set("X-Api-secret", secret);
	}

}
