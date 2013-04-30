package cz.cvut.fel.reposapi.assembla;

import cz.cvut.fel.reposapi.Credentials;

public class AssemblaCredentials implements Credentials {

	public enum Type {
		API_KEY, CLIENT_APPLICATION;
	}

	private final String clientId;
	private final String secret;
	private final Type type;

	public AssemblaCredentials(String clientId, String secret) {
		this(clientId, secret, Type.API_KEY);
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
