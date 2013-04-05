package cz.cvut.fel.bupro.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.User;

@Service
public class UserService {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public List<User> getAllUsers() {
		log.info("get all users");
		return userRepository.findAll();
	}

	@Transactional
	public User getUser(Long id) {
		log.info("get user " + id);
		User user = userRepository.findOne(id);
		user.getComments().size(); // force fetch
		return user;
	}

	@Transactional
	public List<User> getByName(String name, int maxResults) {
		String hint = name + "%";
		return userRepository.findByFirstNameLikeOrLastNameLike(hint, hint, new PageRequest(0, maxResults));
	}

}
