package cz.cvut.fel.bupro.test.model;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Authorship;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
"classpath:testContext.xml"
})
public class ProjectTest {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public User getAuthor() {
		User user = userRepository.findOne(1L);
		if (user != null) {
			return user;
		}
		user = new User();
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
		
		Authorship authorship = new Authorship();
		authorship.setCreationTime(new Timestamp(new Date().getTime()));
		authorship.setCreator(getAuthor());

		project.setAuthorship(authorship);
		
		projectRepository.saveAndFlush(project);

		assert project.getId() != null;
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void shouldPreventNullAuthor() {
		Project project = new Project();
		project.setName("shouldPreventNullAuthor");

		assert project.getAuthorship() == null;
		projectRepository.saveAndFlush(project);
	}
		
	@Test(expected=DataIntegrityViolationException.class)	
	public void shouldPreventNullName() {
		Project project = new Project();
		
		Authorship authorship = new Authorship();
		authorship.setCreationTime(new Timestamp(new Date().getTime()));
		authorship.setCreator(getAuthor());
		project.setAuthorship(authorship);

		assert project.getName() == null;
		projectRepository.saveAndFlush(project);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void shouldPreventDuplicateName() {
		Project project1 = new Project();
		project1.setName("Test Project");
		
		Authorship authorship = new Authorship();
		authorship.setCreationTime(new Timestamp(new Date().getTime()));
		authorship.setCreator(getAuthor());
		
		projectRepository.saveAndFlush(project1);

		Project project2 = new Project();
		project2.setName("Test Project");
		authorship = new Authorship();
		authorship.setCreationTime(new Timestamp(new Date().getTime()));
		authorship.setCreator(getAuthor());
		project2.setAuthorship(authorship);
		
		assert project1.getName().equals(project2.getName());
		
		projectRepository.saveAndFlush(project2);
	}
}
