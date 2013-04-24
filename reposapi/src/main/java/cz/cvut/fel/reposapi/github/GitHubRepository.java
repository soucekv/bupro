package cz.cvut.fel.reposapi.github;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;

import cz.cvut.fel.reposapi.Commit;
import cz.cvut.fel.reposapi.RepositoryException;

public class GitHubRepository implements cz.cvut.fel.reposapi.Repository {

	private Repository repository;
	private CommitService commitService;

	protected GitHubRepository(GitHubClient gitHubClient, Repository repository) {
		this.repository = repository;
		this.commitService = new CommitService(gitHubClient);
	}

	public List<Commit> getCommits() throws RepositoryException {
		try {
			List<RepositoryCommit> repositoryCommits = commitService.getCommits(repository);
			List<Commit> commits = new LinkedList<Commit>();
			for (RepositoryCommit repositoryCommit : repositoryCommits) {
				commits.add(new GitHubCommit(repositoryCommit));
			}
			return commits;
		} catch (IOException e) {
			throw new RepositoryException(e);
		}
	}

	public List<Commit> getCommits(int limit) throws RepositoryException {
		try {
			List<RepositoryCommit> repositoryCommits = commitService.getCommits(repository);
			List<Commit> commits = new LinkedList<Commit>();
			for (RepositoryCommit repositoryCommit : repositoryCommits) {
				if (limit <= 0) {
					return commits;
				}
				commits.add(new GitHubCommit(repositoryCommit));
				limit--;
			}
			return commits;
		} catch (IOException e) {
			throw new RepositoryException(e);
		}
	}

}
