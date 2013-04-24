package cz.cvut.fel.bupro.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.RepositoryLink;
import cz.cvut.fel.reposapi.Commit;
import cz.cvut.fel.reposapi.Credentials;
import cz.cvut.fel.reposapi.Repository;
import cz.cvut.fel.reposapi.RepositoryClient;
import cz.cvut.fel.reposapi.RepositoryClientFactory;
import cz.cvut.fel.reposapi.RepositoryException;
import cz.cvut.fel.reposapi.ServiceProvider;
import cz.cvut.fel.reposapi.github.GitHubCredentials;

@Service
public class CodeRepositoryService {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RepositoryClientFactory factory;

	private RepositoryClient createRepositoryClient(RepositoryLink repositoryLink) {
		Credentials credentials = createCredentials(repositoryLink);
		return factory.create(repositoryLink.getRepositoryProvider(), credentials);
	}

	private RepositoryClient getRepositoryClient(RepositoryLink repositoryLink) {
		if (repositoryLink == null || repositoryLink.getRepositoryProvider() == null) {
			log.warn("No repository provider specified, treating as empty repository");
			return null;
		}
		return createRepositoryClient(repositoryLink);
	}

	public Repository getRepository(Project project) {
		return getRepository(project.getRepository());
	}

	public Repository getRepository(RepositoryLink repositoryLink) {
		RepositoryClient client = getRepositoryClient(repositoryLink);
		try {
			return (client == null) ? null : client.getRepository(repositoryLink.getRepositoryName());
		} catch (RepositoryException e) {
			log.error("Error obtaining repository " + repositoryLink, e);
			return null;
		}
	}

	public List<Commit> getCommits(Project project, int limit) {
		return getCommits(project.getRepository(), limit);
	}

	public List<Commit> getCommits(RepositoryLink repositoryLink, int limit) {
		try {
			Repository repository = getRepository(repositoryLink);
			return (repository == null) ? Collections.<Commit> emptyList() : repository.getCommits(limit);
		} catch (RepositoryException e) {
			log.error("Error obtaining commits " + repositoryLink, e);
			return null;
		}
	}

	public List<Commit> getCommits(Project project) {
		return getCommits(project.getRepository());
	}

	public List<Commit> getCommits(RepositoryLink repositoryLink) {
		try {
			Repository repository = getRepository(repositoryLink);
			return (repository == null) ? Collections.<Commit> emptyList() : repository.getCommits();
		} catch (RepositoryException e) {
			log.error("Error obtaining commits " + repositoryLink, e);
			return null;
		}
	}

	private static Credentials createCredentials(RepositoryLink repositoryLink) {
		ServiceProvider provider = repositoryLink.getRepositoryProvider();
		switch (provider) {
		case GITHUB:
			return new GitHubCredentials(repositoryLink.getRepositoryUser());
		case ASSEMBLA:
		default:
			throw new UnsupportedOperationException("Repository " + StringUtils.capitalize(String.valueOf(provider).toLowerCase()) + " is not supported yet!");
		}
	}

}
