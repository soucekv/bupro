package cz.cvut.fel.reposapi;

import java.util.List;

public interface Repository extends Linkable {
	String getName();

	List<Commit> getCommits() throws RepositoryException;

	List<Commit> getCommits(int limit) throws RepositoryException;

	List<Issue> getIssues() throws RepositoryException;

	List<Issue> getIssues(IssueState issueState) throws RepositoryException;

	List<Issue> getUpdatedIssues(int limit) throws RepositoryException;

	List<Issue> getUpdatedIssues(IssueState issueState, int limit) throws RepositoryException;
}
