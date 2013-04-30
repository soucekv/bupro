package cz.cvut.fel.reposapi.github;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;

import cz.cvut.fel.reposapi.Commit;
import cz.cvut.fel.reposapi.Issue;
import cz.cvut.fel.reposapi.IssueState;
import cz.cvut.fel.reposapi.RepositoryException;

public class GitHubRepository implements cz.cvut.fel.reposapi.Repository {

	private Repository repository;
	private CommitService commitService;
	private IssueService issueService;

	protected GitHubRepository(GitHubClient gitHubClient, Repository repository) {
		this.repository = repository;
		this.commitService = new CommitService(gitHubClient);
		this.issueService = new IssueService(gitHubClient);
	}

	public String getName() {
		return repository.getName();
	}

	public String getExternalUrl() {
		return "https://github.com/" + repository.getOwner().getLogin() + "/" + repository.getName();
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
		if (limit <= 0) {
			return Collections.emptyList();
		}
		try {
			PageIterator<RepositoryCommit> pageIterator = commitService.pageCommits(repository, limit);
			List<Commit> commits = new LinkedList<Commit>();
			for (Collection<RepositoryCommit> page : pageIterator) {
				for (RepositoryCommit repositoryCommit : page) {
					if (limit <= 0) {
						return commits;
					}
					commits.add(new GitHubCommit(repositoryCommit));
					limit--;
				}
			}
			return commits;
		} catch (NoSuchPageException pageException) {
			throw new RepositoryException(pageException.getCause());
		}
	}

	public List<Issue> getIssues() throws RepositoryException {
		List<Issue> issues = new LinkedList<Issue>();
		issues.addAll(getIssues(IssueState.OPEN));
		issues.addAll(getIssues(IssueState.CLOSED));
		return issues;
	}

	public List<Issue> getIssues(IssueState issueState) throws RepositoryException {
		Map<String, String> filterData = new HashMap<String, String>();
		filterData.put("state", issueState.toString().toLowerCase());
		try {
			List<Issue> issues = new LinkedList<Issue>();
			List<org.eclipse.egit.github.core.Issue> list = issueService.getIssues(repository, filterData);
			for (org.eclipse.egit.github.core.Issue issue : list) {
				issues.add(new GitHubIssue(issue));
			}
			return issues;
		} catch (IOException e) {
			throw new RepositoryException(e);
		}
	}

	public List<Issue> getUpdatedIssues(int limit) throws RepositoryException {
		List<Issue> issues = new LinkedList<Issue>();
		issues.addAll(getUpdatedIssues(IssueState.OPEN, limit));
		issues.addAll(getUpdatedIssues(IssueState.CLOSED, limit));
		Collections.sort(issues, Collections.reverseOrder(new Comparator<Issue>() {
			public int compare(Issue o1, Issue o2) {
				Date d1 = o1.getUpdatedAt();
				Date d2 = o2.getUpdatedAt();
				return d1.before(d2) ? -1 : (d1.equals(d2) ? 0 : 1);
			}
		}));
		if (issues.size() > limit) {
			return issues.subList(0, limit);
		} else {
			return issues;
		}
	}

	public List<Issue> getUpdatedIssues(IssueState issueState, int limit) throws RepositoryException {
		if (limit <= 0) {
			return Collections.emptyList();
		}
		Map<String, String> filterData = new HashMap<String, String>();
		filterData.put("state", issueState.toString().toLowerCase());
		filterData.put("sort", "updated");
		try {
			List<Issue> issues = new LinkedList<Issue>();
			PageIterator<org.eclipse.egit.github.core.Issue> pages = issueService.pageIssues(repository, filterData, limit);
			for (Collection<org.eclipse.egit.github.core.Issue> page : pages) {
				for (org.eclipse.egit.github.core.Issue issue : page) {
					if (limit <= 0) {
						return issues;
					}
					issues.add(new GitHubIssue(issue));
					limit--;
				}
			}
			return issues;
		} catch (NoSuchPageException pageException) {
			throw new RepositoryException(pageException.getCause());
		}
	}

}
