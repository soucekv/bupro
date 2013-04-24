package cz.cvut.fel.reposapi.github;

import org.eclipse.egit.github.core.RepositoryCommit;

import cz.cvut.fel.reposapi.Commit;

public class GitHubCommit implements Commit {

	private final RepositoryCommit repositoryCommit;

	public GitHubCommit(RepositoryCommit repositoryCommit) {
		this.repositoryCommit = repositoryCommit;
	}

	public String getUrl() {
		return repositoryCommit.getUrl();
	}

	public String getMessage() {
		return repositoryCommit.getCommit().getMessage();
	}

}
