package cz.cvut.fel.reposapi;

/**
 * This interface provides API for manipulation with repository service
 * 
 * @author Viktor Soucek
 * 
 */
public interface RepositoryClient {

	/**
	 * Finds repository by name
	 * 
	 * @param name
	 *            of repository
	 * @return instance representing repository
	 * @throws RepositoryException
	 */
	Repository getRepository(String name) throws RepositoryException;
}
