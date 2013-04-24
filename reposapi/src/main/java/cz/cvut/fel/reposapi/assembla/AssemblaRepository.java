package cz.cvut.fel.reposapi.assembla;

import java.util.List;

import cz.cvut.fel.reposapi.Commit;
import cz.cvut.fel.reposapi.Repository;
import cz.cvut.fel.reposapi.RepositoryException;

public class AssemblaRepository implements Repository {

	private final String name;

	public AssemblaRepository(String name) {
		this.name = name;
	}

	public List<Commit> getCommits() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Commit> getCommits(int limit) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

}
