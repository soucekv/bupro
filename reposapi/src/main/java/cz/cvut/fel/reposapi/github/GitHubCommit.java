package cz.cvut.fel.reposapi.github;

import org.eclipse.egit.github.core.RepositoryCommit;

import cz.cvut.fel.reposapi.Commit;

public class GitHubCommit implements Commit {

	private final RepositoryCommit repositoryCommit;

	public GitHubCommit(RepositoryCommit repositoryCommit) {
		this.repositoryCommit = repositoryCommit;
	}

	public String getExternalUrl() {
		String url = repositoryCommit.getUrl();
		url = url.replace("https://api.github.com/repos", "https://github.com");
		url = url.replace("commits", "commit");
		return url;
	}

	public String getMessage() {
		return repositoryCommit.getCommit().getMessage();
	}

	public String getAuthorName() {
		return repositoryCommit.getAuthor().getName();
	}

	public String getId() {
		return repositoryCommit.getSha();
	}

}
