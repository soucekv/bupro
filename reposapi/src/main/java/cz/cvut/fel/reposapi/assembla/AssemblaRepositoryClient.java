package cz.cvut.fel.reposapi.assembla;

import cz.cvut.fel.reposapi.AbstractRepositoryClient;
import cz.cvut.fel.reposapi.Credentials;
import cz.cvut.fel.reposapi.Repository;
import cz.cvut.fel.reposapi.RepositoryClient;

public class AssemblaRepositoryClient extends AbstractRepositoryClient<AssemblaCredentials> implements RepositoryClient {

	public AssemblaRepositoryClient(Credentials credentials) {
		super(AssemblaCredentials.class, credentials);
	}

	public Repository getRepository(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
