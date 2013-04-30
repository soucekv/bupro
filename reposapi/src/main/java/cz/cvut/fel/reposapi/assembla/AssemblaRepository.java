package cz.cvut.fel.reposapi.assembla;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cz.cvut.fel.reposapi.Commit;
import cz.cvut.fel.reposapi.Issue;
import cz.cvut.fel.reposapi.IssueState;
import cz.cvut.fel.reposapi.Repository;
import cz.cvut.fel.reposapi.RepositoryException;
import cz.cvut.fel.reposapi.assembla.client.Space;
import cz.cvut.fel.reposapi.assembla.client.Ticket;

public class AssemblaRepository implements Repository {

	private final Space space;
	private final AssemblaRepositoryClient repositoryClient;

	public AssemblaRepository(Space space, AssemblaRepositoryClient repositoryClient) {
		this.space = space;
		this.repositoryClient = repositoryClient;
	}

	public String getName() {
		return space.getName();
	}

	public String getExternalUrl() {
		return "https://www.assembla.com/spaces/show/" + space.getName();
	}

	public List<Commit> getCommits() throws RepositoryException {
		// Not supported by Assembla
		return Collections.emptyList();
	}

	public List<Commit> getCommits(int limit) throws RepositoryException {
		// Not supported by Assembla
		return Collections.emptyList();
	}

	private List<Issue> createAssambleIssues(List<Ticket> tickets) {
		List<Issue> issues = new LinkedList<Issue>();
		for (Ticket ticket : tickets) {
			issues.add(new AssemblaIssue(space, ticket));
		}
		return issues;
	}

	public List<Issue> getIssues() throws RepositoryException {
		return createAssambleIssues(repositoryClient.getIssues(space));
	}

	public List<Issue> getIssues(IssueState issueState) throws RepositoryException {
		return createAssambleIssues(repositoryClient.getIssues(space, translate(issueState)));
	}

	public List<Issue> getUpdatedIssues(int limit) throws RepositoryException {
		return createAssambleIssues(repositoryClient.getUpdatedIssues(space, limit));
	}

	public List<Issue> getUpdatedIssues(IssueState issueState, int limit) throws RepositoryException {
		return createAssambleIssues(repositoryClient.getUpdatedIssues(space, translate(issueState), limit));
	}

	private static Ticket.State translate(IssueState issueState) {
		return (issueState == IssueState.OPEN) ? Ticket.State.OPEN : Ticket.State.CLOSED;
	}

}
