package cz.cvut.fel.bupro.test.model;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.MembershipRepository;
import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Membership;
import cz.cvut.fel.bupro.model.MembershipState;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.test.configuration.TestJpaConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestJpaConfig.class})
@Transactional
public class MembershipTest {

	@Autowired
	private MembershipRepository membershipRepository;

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
		user.setPassword("blah");
		userRepository.save(user);
		return user;
	}

	private Project createProject(String name, User author) {
		Project project = new Project();
		project.setName(name);
		project.setOwner(author);
		projectRepository.save(project);
		return project;
	}

	@Test(expected = PersistenceException.class)
	public void testPreventNullProject() {
		User user = createUser("Karel", "Novak", "novak@exmple.com", "novak");

		Membership membership = new Membership();
		membership.setUser(user);

		membership.setProject(null);

		membershipRepository.save(membership);
	}

	@Test(expected = PersistenceException.class)
	public void testPreventNullUser() {
		User author = createUser("Frantisek", "Vomacka", "vomacka@exmple.com", "vomacka");
		Project project = createProject("Test Project", author);

		Membership membership = new Membership();
		membership.setProject(project);

		membership.setUser(null);

		membershipRepository.save(membership);
	}

	@Test(expected = PersistenceException.class)
	public void testPreventDuplicateMembership() {
		User user = createUser("Karel", "Novak", "novak@exmple.com", "novak");
		User author = createUser("Frantisek", "Vomacka", "vomacka@exmple.com", "vomacka");
		Project project = createProject("Test Project", author);

		if (project.isAutoApprove()) {
			project.setAutoApprove(false);
			projectRepository.save(project);
		}

		Membership membership1 = new Membership();
		membership1.setProject(project);
		membership1.setUser(user);

		membershipRepository.save(membership1);

		Membership membership2 = new Membership();
		membership2.setProject(project);
		membership2.setUser(user);

		membershipRepository.save(membership2);
	}

	@Test
	public void testManualApproveMemberToProject() {
		User user = createUser("Karel", "Novak", "novak@exmple.com", "novak");
		User author = createUser("Frantisek", "Vomacka", "vomacka@exmple.com", "vomacka");
		Project project = createProject("Test Project", author);

		if (project.isAutoApprove()) {
			project.setAutoApprove(false);
			projectRepository.save(project);
		}

		Membership membership = new Membership();
		membership.setProject(project);
		membership.setUser(user);

		membershipRepository.save(membership);

		assert membership.getMembershipState() == MembershipState.WAITING_APPROVAL;
	}

	@Test
	public void testAutoApporveMemberToProject() {
		User user = createUser("Karel", "Novak", "novak@exmple.com", "novak");
		User author = createUser("Frantisek", "Vomacka", "vomacka@exmple.com", "vomacka");
		Project project = createProject("Test Project", author);

		if (!project.isAutoApprove()) {
			project.setAutoApprove(true);
			projectRepository.save(project);
		}

		Membership membership = new Membership();
		membership.setProject(project);
		membership.setUser(user);

		membershipRepository.save(membership);

		assert membership.getMembershipState() == MembershipState.APPROVED;
	}

}
