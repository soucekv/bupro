package cz.cvut.fel.bupro.test.model;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.test.configuration.JpaTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaTestConfig.class})
@Transactional
public class UserTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void shouldPersistUser() {
		User user = new User();
		user.setFirstName("Frantisek");
		user.setLastName("Vomacka");
		user.setEmail("vomacka@exmple.com");
		user.setUsername("vomacka");
		user.setPassword("blah");

		userRepository.saveAndFlush(user);

		assert user.getId() != null;
	}

	@Test(expected = ConstraintViolationException.class)
	public void shouldPreventNullUsername() {
		User user = new User();
		user.setFirstName("Frantisek");
		user.setLastName("Vomacka");
		user.setEmail("vomacka@exmple.com");
		user.setPassword("blah");

		userRepository.saveAndFlush(user);
	}

	@Test(expected = PersistenceException.class)
	public void shouldPreventDuplicateUsername() {
		User user1 = new User();
		user1.setFirstName("Frantisek");
		user1.setLastName("Vomacka");
		user1.setUsername("vomacka");
		user1.setEmail("vomacka@exmple.com");
		user1.setPassword("blah");

		userRepository.saveAndFlush(user1);

		User user2 = new User();
		user2.setFirstName("Karel");
		user2.setLastName("Vomacka");
		user2.setUsername("vomacka");
		user2.setEmail("karel@exmple.com");
		user2.setPassword("blah");

		assert user1.getUsername().equals(user2.getUsername());

		userRepository.saveAndFlush(user2);
	}

	@Test(expected = PersistenceException.class)
	public void shouldPreventNullEmail() {
		User user = new User();
		user.setFirstName("Frantisek");
		user.setLastName("Vomacka");
		user.setUsername("vomacka");
		user.setPassword("blah");

		userRepository.saveAndFlush(user);
	}

	@Test(expected = PersistenceException.class)
	public void shouldPreventDuplicateEmail() {
		User user1 = new User();
		user1.setFirstName("Frantisek");
		user1.setLastName("Vomacka");
		user1.setUsername("vomacka");
		user1.setEmail("vomacka@exmple.com");
		user1.setPassword("blah");

		userRepository.saveAndFlush(user1);

		User user2 = new User();
		user2.setFirstName("Karel");
		user2.setLastName("Vomacka");
		user2.setUsername("karelvomacka");
		user2.setEmail("vomacka@exmple.com");
		user2.setPassword("blah");

		assert user1.getEmail().equals(user2.getEmail());

		userRepository.saveAndFlush(user2);
	}

}
