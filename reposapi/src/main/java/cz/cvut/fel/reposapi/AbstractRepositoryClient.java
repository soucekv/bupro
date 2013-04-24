package cz.cvut.fel.reposapi;

/**
 * Common implementation ensures correct type of {@link Credentials} in
 * constructor and common behavior in case of error.
 * 
 * @author Viktor Souček
 * 
 * @param <T>
 *            repository client implementation specific type of
 *            {@link Credentials}
 */
public abstract class AbstractRepositoryClient<T extends Credentials> implements RepositoryClient {
	private final T credentials;

	public AbstractRepositoryClient(Class<T> type, Credentials credentials) {
		if (credentials == null) {
			throw new NullPointerException(this.getClass().getName() + " requires " + type.getName() + " implementation!");
		}
		if (type.isAssignableFrom(credentials.getClass())) {
			this.credentials = type.cast(credentials);
		} else {
			throw new IllegalArgumentException(this.getClass().getName() + " requires " + type.getName() + " implementation!");
		}
	}

	protected T getCredentials() {
		return credentials;
	}
}
