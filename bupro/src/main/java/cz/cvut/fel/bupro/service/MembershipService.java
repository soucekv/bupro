package cz.cvut.fel.bupro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.MembershipRepository;
import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Membership;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

@Service
public class MembershipService {

	@Autowired
	private MembershipRepository membershipRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public Membership getMembership(Long projectId, Long userId) {
		Project project = projectRepository.findOne(projectId);
		User user = userRepository.findOne(userId);
		return membershipRepository.findByProjectAndUser(project, user);
	}
}
