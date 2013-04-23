package cz.cvut.fel.kos;

import java.util.List;

import cz.cvut.fel.kos.jaxb.Course;
import cz.cvut.fel.kos.jaxb.Semester;
import cz.cvut.fel.kos.jaxb.Student;
import cz.cvut.fel.kos.jaxb.StudyState;
import cz.cvut.fel.kos.jaxb.Teacher;

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

	/**
	 * Finds all students currently studying in other words
	 * {@link StudyState#ACTIVE}
	 * 
	 * @return
	 */
	List<Student> getStudents();

	/**
	 * Finds student by KOS username
	 * 
	 * @param username
	 * @return instance of {@link Student} or <code>null</code> if no such
	 *         student exists
	 */
	Student getStudent(String username);

	/**
	 * Finds teacher by KOS username
	 * 
	 * @param username
	 * @return instance of {@link Teacher} or <code>null</code> if no such
	 *         teacher exists
	 */
	Teacher getTeacher(String username);

	// TODO more methods
}
