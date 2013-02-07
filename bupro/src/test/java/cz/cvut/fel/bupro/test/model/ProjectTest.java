package cz.cvut.fel.bupro.test.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Authorship;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" })
@Transactional
public class ProjectTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	public User createUser() {
		User user = new User();
		user.setFirstName("Frantisek");
		user.setLastName("Vomacka");
		user.setEmail("vomacka@exmple.com");
		user.setUsername("vomacka");

		userRepository.save(user);
		return user;
	}

	@Test
	public void shouldPersistProject() {
		Project project = new Project();
		project.setName("Test persist Project");

		project.setAuthorship(new Authorship(createUser()));

		projectRepository.save(project);

		assert project.getId() != null;
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void shouldPreventNullAuthor() {
		Project project = new Project();
		project.setName("shouldPreventNullAuthor");

		assert project.getAuthorship() == null;
		projectRepository.save(project);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void shouldPreventNullName() {
		Project project = new Project();

		project.setAuthorship(new Authorship(createUser()));

		assert project.getName() == null;
		projectRepository.save(project);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void shouldPreventDuplicateName() {
		User user = createUser();

		Project project1 = new Project();
		project1.setName("Test Project");
		project1.setAuthorship(new Authorship(user));
		projectRepository.save(project1);

		Project project2 = new Project();
		project2.setName("Test Project");
		project2.setAuthorship(new Authorship(user));

		assert project1.getName().equals(project2.getName());

		projectRepository.save(project2);
	}
}
