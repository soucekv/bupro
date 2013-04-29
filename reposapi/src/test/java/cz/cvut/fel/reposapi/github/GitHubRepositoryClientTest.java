package cz.cvut.fel.reposapi.github;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cz.cvut.fel.reposapi.Commit;
import cz.cvut.fel.reposapi.Credentials;
import cz.cvut.fel.reposapi.Issue;
import cz.cvut.fel.reposapi.Repository;
import cz.cvut.fel.reposapi.RepositoryClient;
import cz.cvut.fel.reposapi.RepositoryException;

/**
 * Test obtaining data from existing public repository
 *
 */
public class GitHubRepositoryClientTest {

	private String name;
	private Credentials credentials;

	@Before
	public void createCredentials() {
		credentials = new GitHubCredentials("mneorr");
		name = "Alcatraz";
	}

	@Test
	public void testGetOneCommit() throws RepositoryException {
		RepositoryClient repositoryClient = new GitHubRepositoryClient(credentials);
		Repository repository = repositoryClient.getRepository(name);

		List<Commit> commits = repository.getCommits(1);

		Assert.assertEquals(commits.size(), 1);
	}

	@Test
	public void testGetOneIssue() throws RepositoryException {
		RepositoryClient repositoryClient = new GitHubRepositoryClient(credentials);
		Repository repository = repositoryClient.getRepository(name);

		List<Issue> issues = repository.getUpdatedIssues(1);

		Assert.assertEquals(issues.size(), 1);
	}
}
