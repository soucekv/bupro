package cz.cvut.fel.bupro.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
		return userRepository.findOne(id);
	}
}