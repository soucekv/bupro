package cz.cvut.fel.kos;

/**
 * Encapsulates required parameters by KOS Client.
 */
public class Configuration {

	private final String uri;
	private final Authentication authentication;
	private final String username;
	private final String password;

	/**
	 *
	 * @param uri
	 *            KOS API REST url base
	 * @param username
	 *            KOS API authentication username
	 * @param password
	 *            KOS API authentication password
	 * @param authentication
	 * 			  KOS API authentication scheme {@link Authentication}
	 */
	public Configuration(String uri, String username, String password, Authentication authentication) {
		if (uri == null) {
			throw new NullPointerException("KOS URI required, but is null");
		}
		if (uri.trim().isEmpty()) {
			throw new IllegalArgumentException("KOS URI required, but is empty");
		}
		if (authentication == null) {
			throw new NullPointerException("KOS Authentication type required");
		}
		this.uri = uri;
		this.authentication = authentication;
		this.username = username;
		this.password = password;
	}

	public String getUri() {
		return uri;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuration other = (Configuration) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	/**
	 * KOS client supported authentication schemes
	 */
	public enum Authentication {
		BASIC, OAUTH2;
	}

}
