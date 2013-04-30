package cz.cvut.fel.reposapi.assembla;

import cz.cvut.fel.reposapi.Credentials;

/**
 * Assembla credentials support credentials based on registered application
 * clientId (Application identifier) and secret (Application secret). Also it is
 * possible to use user root access using Api key and secret (not recommended
 * for production). See API applications & sessions in your Assembla profile.
 * 
 * @author Viktor Souček
 * 
 */
public class AssemblaCredentials implements Credentials {

	public enum Type {
		API_KEY, CLIENT_APPLICATION;
	}

	private final String clientId;
	private final String secret;
	private final Type type;

	/**
	 * New Assembla Credentials with type {@link Type#CLIENT_APPLICATION}
	 * 
	 * @param clientId
	 * @param secret
	 */
	public AssemblaCredentials(String clientId, String secret) {
		this(clientId, secret, Type.CLIENT_APPLICATION);
	}

	public AssemblaCredentials(String clientId, String secret, Type type) {
		this.clientId = clientId;
		this.secret = secret;
		this.type = type;
	}

	public String getClientId() {
		return clientId;
	}

	public String getSecret() {
		return secret;
	}

	public Type getType() {
		return type;
	}

}
