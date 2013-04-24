package cz.cvut.fel.reposapi;

/**
 * Top level interface for library. Provides method for obtaining repository
 * client implementations.
 * 
 * @author Viktor Soucek
 * 
 */
public interface RepositoryClientFactory {

	/**
	 * Creates new repository client with provided credentials.
	 * 
	 * @param serviceProvider
	 *            type of service provider supported by library
	 * @param credentials
	 * @return client instance
	 */
	RepositoryClient create(ServiceProvider serviceProvider, Credentials credentials);
}
