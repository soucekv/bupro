package cz.cvut.fel.bupro.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.CourseRepository;
import cz.cvut.fel.bupro.model.ProjectCourse;
import cz.cvut.fel.kos.KosClient;
import cz.cvut.fel.kos.Translator;
import cz.cvut.fel.kos.jaxb.Course;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private KosClient kos;

	@Transactional
	public List<ProjectCourse> getProjectCourses() {
		return courseRepository.findAll();
	}

	@Transactional
	public List<ProjectCourse> getProjectCourses(Locale locale) {
		List<ProjectCourse> courses = getProjectCourses();
		Translator translator = new Translator(locale);
		for (ProjectCourse projectCourse : courses) {
			Course course = kos.getCourse(projectCourse.getCode());
			String name = course.getCode() + " - " + translator.localizedString(course.getName());
			projectCourse.setName(name);
		}
		return courses;
	}

	@Transactional
	public ProjectCourse getProjectCourse(Long id) {
		ProjectCourse course = courseRepository.findOne(id);
		course.getProjects().size();
		return course;
	}

	@Transactional
	public ProjectCourse save(ProjectCourse course) {
		return courseRepository.save(course);
	}
}
