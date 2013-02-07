package cz.cvut.fel.bupro.test.model;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Authorship;
import cz.cvut.fel.bupro.model.Comment;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" })
public class CommentTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectRepository projectRepository;

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

	public Project getProject() {
		List<Project> projects = projectRepository.findAll();
		Project project = null;
		if (projects.isEmpty()) {
			project = new Project();
			project.setAuthorship(new Authorship(getAuthor()));
			project.setName("Comment Test Project");
			projectRepository.save(project);
		} else {
			project = projects.get(0);
		}
		return project;
	}

	@Test
	public void testCommentOnUser() {
		User user = new User();
		user = new User();
		user.setFirstName("Karel");
		user.setLastName("Novak");
		user.setEmail("novak@exmple.com");
		user.setUsername("novak");
		userRepository.save(user);

		User author = getAuthor();

		Comment comment = new Comment();
		comment.setAuthorship(new Authorship(author));

		comment.setTitle("Test comment");
		comment.setText("Some text");

		user.add(comment);
		userRepository.save(user);
	}

	@Test
	public void testCommentOnSelf() {
		User author = getAuthor();
		User user = author;

		Comment comment = new Comment();
		comment.setAuthorship(new Authorship(author));
		comment.setTitle("Test comment");
		comment.setText("Some text");

		user.add(comment);
		userRepository.save(user);
	}

	@Test
	public void testCommentOnProject() {
		Project project = getProject();

		Comment comment = new Comment();
		comment.setAuthorship(new Authorship(getAuthor()));
		comment.setTitle("Test project comment");
		comment.setText("Some text");

		project.add(comment);

		projectRepository.save(project);
	}

}
