package cz.cvut.fel.reposapi.assembla;

import cz.cvut.fel.reposapi.Credentials;

public class AssemblaCredentials implements Credentials {

	private final String clientId;

	public AssemblaCredentials(String clientId) {
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}
}
