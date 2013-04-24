package cz.cvut.fel.reposapi;

import cz.cvut.fel.reposapi.assembla.AssemblaRepositoryClient;
import cz.cvut.fel.reposapi.github.GitHubRepositoryClient;

public class DefaultRepositoryClientFactory implements RepositoryClientFactory {

	private static final DefaultRepositoryClientFactory INSTANCE = new DefaultRepositoryClientFactory();

	private DefaultRepositoryClientFactory() {
		// Intentionally blank
	}

	public RepositoryClient create(ServiceProvider serviceProvider, Credentials credentials) {
		switch (serviceProvider) {
		case GITHUB:
			return new GitHubRepositoryClient(credentials);
		case ASSEMBLA:
			return new AssemblaRepositoryClient(credentials);
		default:
			throw new IllegalStateException("Unknown repository service provider " + serviceProvider);
		}
	}

	public static RepositoryClientFactory getInstance() {
		return DefaultRepositoryClientFactory.INSTANCE;
	}
}
