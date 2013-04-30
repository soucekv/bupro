package cz.cvut.fel.reposapi.assembla;

import java.util.List;

import cz.cvut.fel.reposapi.AbstractRepositoryClient;
import cz.cvut.fel.reposapi.Credentials;
import cz.cvut.fel.reposapi.Repository;
import cz.cvut.fel.reposapi.RepositoryClient;
import cz.cvut.fel.reposapi.assembla.AssemblaCredentials.Type;
import cz.cvut.fel.reposapi.assembla.client.ApiKey;
import cz.cvut.fel.reposapi.assembla.client.AssemblaClient;
import cz.cvut.fel.reposapi.assembla.client.Identity;
import cz.cvut.fel.reposapi.assembla.client.Space;
import cz.cvut.fel.reposapi.assembla.client.Token;

public class AssemblaRepositoryClient extends AbstractRepositoryClient<AssemblaCredentials> implements RepositoryClient {

	private final AssemblaClient assemblaClient;
	private Token token;

	public AssemblaRepositoryClient(Credentials credentials) {
		super(AssemblaCredentials.class, credentials);
		if (getCredentials().getType() == null) {
			throw new NullPointerException(AssemblaCredentials.class.getName() + " type is required!");
		}
		assemblaClient = new AssemblaClient();
	}

	private Identity getIdentity() {
		if (getCredentials().getType() == Type.CLIENT_APPLICATION) {
			if (token == null || token.isExpired()) {
				token = assemblaClient.getClientToken(getCredentials().getClientId(), getCredentials().getSecret());
			}
			return token;
		}
		if (getCredentials().getType() == Type.API_KEY) {
			return new ApiKey(getCredentials().getClientId(), getCredentials().getSecret());
		}
		throw new IllegalStateException("Uknown type of credentials " + getCredentials().getType());
	}

	public List<Space> getSpaces() {
		return assemblaClient.getSpaces(getIdentity());
	}

	public Repository getRepository(String name) {
		List<Space> spaces = assemblaClient.getSpaces(getIdentity());
		for (Space space : spaces) {
			if (space.getName().equals(name)) {
				return new AssemblaRepository(space, this);
			}
		}
		return null;
	}

	public void getCommits(Space space) {

	}

}
