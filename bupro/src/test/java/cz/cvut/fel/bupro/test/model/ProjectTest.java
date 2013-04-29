package cz.cvut.fel.bupro.test.model;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.SemesterCode;
import cz.cvut.fel.bupro.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" })
public class ProjectTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	private SemesterCode semesterCode = new SemesterCode("B132");

	public User createUser() {
		User user = new User();
		user.setFirstName("Frantisek");
		user.setLastName("Vomacka");
		user.setEmail("vomacka@exmple.com");
		user.setUsername("vomacka");
		user.setPassword("blah");
		userRepository.save(user);
		return user;
	}

	@Test
	@Transactional
	public void shouldPersistProject() {
		Project project = new Project();
		project.setName("Test persist Project");
		project.setStartSemester(semesterCode);
		project.setEndSemester(semesterCode);
		project.setOwner(createUser());

		projectRepository.save(project);

		assert project.getId() != null;
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	public void shouldPreventNullAuthor() {
		Project project = new Project();
		project.setName("shouldPreventNullAuthor");
		project.setStartSemester(semesterCode);
		project.setEndSemester(semesterCode);

		assert project.getOwner() == null;
		projectRepository.save(project);
	}

	@Test(expected = ConstraintViolationException.class)
	@Transactional
	public void shouldPreventNullName() {
		Project project = new Project();
		project.setStartSemester(semesterCode);
		project.setEndSemester(semesterCode);
		project.setOwner(createUser());

		assert project.getName() == null;
		projectRepository.save(project);
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	public void shouldPreventDuplicateName() {
		User user = createUser();

		String name = "Test";
		Project project1 = new Project();
		project1.setName(name);
		project1.setOwner(user);
		project1.setStartSemester(new SemesterCode("B132"));
		project1.setEndSemester(new SemesterCode("B132"));
		project1 = projectRepository.save(project1);
		assert project1.getId() != null;

		Project project2 = new Project();
		project2.setName(name);
		project2.setOwner(user);
		project2.setStartSemester(new SemesterCode("B132"));
		project2.setEndSemester(new SemesterCode("B132"));

		assert project1.getName().equals(project2.getName());

		project2 = projectRepository.save(project2);
		assert project2.getId() != null;
	}
}
