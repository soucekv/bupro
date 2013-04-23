package cz.cvut.fel.bupro.test.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.CourseRepository;
import cz.cvut.fel.bupro.model.ProjectCourse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" })
@Transactional
public class SubjectTest {

	@Autowired
	private CourseRepository courseRepository;

	@Test
	public void shouldPersistSubject() {
		ProjectCourse course = new ProjectCourse();
		course.setName("Test Course");
		courseRepository.save(course);
		assert course.getId() != null;
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void shouldPreventNullName() {
		ProjectCourse course = new ProjectCourse();
		course.setName(null);
		courseRepository.save(course);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void shouldPreventDuplicateName() {
		ProjectCourse subject1 = new ProjectCourse();
		subject1.setName("Test Course");
		courseRepository.save(subject1);
		ProjectCourse subject2 = new ProjectCourse();
		subject2.setName("Test Course");
		courseRepository.save(subject2);
	}
}
