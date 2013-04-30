package cz.cvut.fel.reposapi.assembla;

import java.util.Collections;
import java.util.List;

import cz.cvut.fel.reposapi.Commit;
import cz.cvut.fel.reposapi.Issue;
import cz.cvut.fel.reposapi.IssueState;
import cz.cvut.fel.reposapi.Repository;
import cz.cvut.fel.reposapi.RepositoryException;
import cz.cvut.fel.reposapi.assembla.client.Space;

public class AssemblaRepository implements Repository {

	private final Space space;
	private final AssemblaRepositoryClient repositoryClient;

	public AssemblaRepository(Space space, AssemblaRepositoryClient repositoryClient) {
		this.space = space;
		this.repositoryClient = repositoryClient;
	}

	public String getExternalUrl() {
		return "https://www.assembla.com/spaces/show/" + space.getName();
	}

	public List<Commit> getCommits() throws RepositoryException {
		// List<> repositoryClient.getCommits();
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	public List<Commit> getCommits(int limit) throws RepositoryException {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	public List<Issue> getIssues() throws RepositoryException {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	public List<Issue> getIssues(IssueState issueState) throws RepositoryException {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	public List<Issue> getUpdatedIssues(int limit) throws RepositoryException {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	public List<Issue> getUpdatedIssues(IssueState issueState, int limit) throws RepositoryException {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

}
