package cz.cvut.fel.bupro.security;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.config.Qualifiers;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.User;

@Service
@Qualifier(Qualifiers.MOCK)
public class MockSecurityService implements SecurityService {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User getCurrentUser() {
		log.warn("MockSecurityService.getCurrentUser");
		List<User> users = userRepository.findAll();
		return users.isEmpty() ? null : users.get(0);
	}

	@Transactional
	public User createUser(User user) {
		log.warn("MockSecurityService.createUser");
		return userRepository.save(user);
	}

}
