package cz.cvut.fel.reposapi.assembla.client;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

public class BasicAuth {

	public static String encodeBase64(String value) {
		byte[] encodedAuth = Base64.encodeBase64(value.getBytes(Charset.forName("US-ASCII")));
		return new String(encodedAuth);
	}

	public static String createAuthString(String username, String password) {
		return "Basic " + encodeBase64(username + ":" + password);
	}

	public static HttpHeaders createAuthHeaders(String username, String password) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", createAuthString(username, password));
		return httpHeaders;
	}

}
