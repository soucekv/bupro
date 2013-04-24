package cz.cvut.fel.reposapi;

/**
 * This interface defines API for obtaining commit information
 * 
 * @author Viktor Souček
 * 
 */
public interface Commit {

	/**
	 * 
	 * @return URL to this commit on provider's web
	 */
	String getExternalUrl();

	/**
	 * 
	 * @return commit message
	 */
	String getMessage();

	/**
	 * id of commit for Git SHA
	 * 
	 * @return
	 */
	String getId();
}
