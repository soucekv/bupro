package cz.cvut.fel.reposapi.github;

import cz.cvut.fel.reposapi.Credentials;

public class GitHubCredentials implements Credentials {
	private final String username;
	private final String password;

	/**
	 * Public access only to repository owned by specified user
	 * 
	 * @param username
	 *            owner of repository
	 */
	public GitHubCredentials(String username) {
		this(username, null);
	}

	public GitHubCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
