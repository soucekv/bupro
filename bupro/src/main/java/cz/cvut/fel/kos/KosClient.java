package cz.cvut.fel.kos;

import java.util.List;
import java.util.Locale;

import cz.cvut.fel.kos.jaxb.Semester;

/**
 * High level API for obtaining data from KOS
 * 
 * @author Viktor Souček
 */
public interface KosClient {

	/**
	 * 
	 * @param code
	 *            KOS semester code
	 * @return semester instance identified by code parameter
	 * @throws IllegalArgumentException
	 *             if code is not valid KOS Semester code
	 */
	Semester getSemester(String code);

	/**
	 * 
	 * @return list of all semesters
	 */
	List<Semester> getSemesters();

	/**
	 * 
	 * @return previous semester
	 */
	Semester getPreviousSemester();

	/**
	 * 
	 * @return current semester
	 */
	Semester getSemester();

	/**
	 * 
	 * @return next semester
	 */
	Semester getNextSemester();

	/**
	 * Localized semester name, if there is no name defined for locale this
	 * method should return English name
	 * 
	 * @param code
	 * @param locale
	 * @return Localized semester name
	 */
	String getSemesterName(String code, Locale locale);

	// TODO more methods
}
