package cz.cvut.fel.kos;

import cz.cvut.fel.kos.jaxb.Semester;

/**
 * High level API for obtaining data from KOS
 */
public interface KosClient {

	/**
	 *
	 * @return current semester
	 */
	Semester getSemmester();

	// TODO more methods
}
