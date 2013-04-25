package cz.cvut.fel.reposapi;

/**
 * This interface defines API for obtaining commit information
 * 
 * @author Viktor Souček
 * 
 */
public interface Commit extends Linkable {

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
