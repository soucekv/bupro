package cz.cvut.fel.kos;

import java.util.List;
import java.util.Locale;

import cz.cvut.fel.kos.jaxb.Course;
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
	 * @throws NullPointerException
	 *             if code is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if code is not valid
	 */
	String getSemesterName(String code, Locale locale);

	/**
	 * List of approved courses
	 * 
	 * @return
	 */
	List<Course> getCourses();

	/**
	 * Finds course by KOS code i.e. X36PMI
	 * 
	 * @param code
	 * @return instance of {@link Course} or <code>null</code> if no such course
	 *         exists
	 * @throws NullPointerException
	 *             if code is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if code is not valid
	 */
	Course getCourse(String code);

	// TODO more methods
}
