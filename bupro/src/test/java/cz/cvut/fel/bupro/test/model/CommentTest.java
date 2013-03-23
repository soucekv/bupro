package cz.cvut.fel.bupro.test.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Comment;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" })
@Transactional
public class CommentTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectRepository projectRepository;

	private User createUser(String firstName, String lastName, String email, String username) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setUsername(username);
		userRepository.save(user);
		return user;
	}

	private Project createProject(String name, User owner) {
		Project project = new Project();
		project.setName(name);
		project.setOwner(owner);
		projectRepository.save(project);
		return project;
	}

	@Test
	public void testCommentOnUser() {
		User user = createUser("Karel", "Novak", "novak@exmple.com", "novak");
		User author = createUser("Frantisek", "Vomacka", "vomacka@exmple.com", "vomacka");

		Comment comment = new Comment();
		comment.setUser(author);

		comment.setTitle("Test comment");
		comment.setText("Some text");

		user.add(comment);
		userRepository.save(user);
	}

	@Test
	public void testCommentOnSelf() {
		User author = createUser("Frantisek", "Vomacka", "vomacka@exmple.com", "vomacka");
		User user = author;

		Comment comment = new Comment();
		comment.setUser(author);
		comment.setTitle("Test comment");
		comment.setText("Some text");

		user.add(comment);
		userRepository.save(user);
	}

	@Test
	public void testCommentOnProject() {
		User author = createUser("Frantisek", "Vomacka", "vomacka@exmple.com", "vomacka");
		Project project = createProject("Test Project", author);

		Comment comment = new Comment();
		comment.setUser(author);
		comment.setTitle("Test project comment");
		comment.setText("Some text");

		project.add(comment);

		projectRepository.save(project);
	}

}
