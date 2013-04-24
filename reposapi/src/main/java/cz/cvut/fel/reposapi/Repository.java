package cz.cvut.fel.reposapi;

import java.util.List;

public interface Repository {
	List<Commit> getCommits() throws RepositoryException;

	List<Commit> getCommits(int limit) throws RepositoryException;
}
