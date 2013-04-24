package cz.cvut.fel.reposapi.github;

import java.io.IOException;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import cz.cvut.fel.reposapi.AbstractRepositoryClient;
import cz.cvut.fel.reposapi.Credentials;
import cz.cvut.fel.reposapi.RepositoryClient;
import cz.cvut.fel.reposapi.RepositoryException;

public class GitHubRepositoryClient extends AbstractRepositoryClient<GitHubCredentials> implements RepositoryClient {

	private GitHubClient gitHubClient;
	private RepositoryService repositoryService;

	public GitHubRepositoryClient(Credentials credentials) {
		super(GitHubCredentials.class, credentials);
		GitHubCredentials gitHubCredentials = getCredentials();
		gitHubClient = new GitHubClient();
		if (gitHubCredentials.getPassword() != null) {
			gitHubClient.setCredentials(gitHubCredentials.getUsername(), gitHubCredentials.getPassword());
		}
		repositoryService = new RepositoryService(gitHubClient);
	}

	public cz.cvut.fel.reposapi.Repository getRepository(String name) throws RepositoryException {
		try {
			Repository repository = repositoryService.getRepository(getCredentials().getUsername(), name);
			if (repository == null) {
				throw new RepositoryException("Repository by name " + name + " not found!");
			}
			return new GitHubRepository(gitHubClient, repository);
		} catch (IOException e) {
			throw new RepositoryException(e);
		}
	}

}
