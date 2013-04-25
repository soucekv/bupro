package cz.cvut.fel.reposapi.github;

import java.util.Date;

import org.eclipse.egit.github.core.Issue;

import cz.cvut.fel.reposapi.IssueState;

public class GitHubIssue implements cz.cvut.fel.reposapi.Issue {
	private final Issue issue;

	public GitHubIssue(Issue issue) {
		this.issue = issue;
	}

	public String getTitle() {
		return issue.getTitle();
	}

	public IssueState getState() {
		String state = issue.getState();
		return IssueState.valueOf(state.toUpperCase());
	}

	public String getExternalUrl() {
		return issue.getHtmlUrl();
	}

	public Date getUpdatedAt() {
		return issue.getUpdatedAt();
	}

}
