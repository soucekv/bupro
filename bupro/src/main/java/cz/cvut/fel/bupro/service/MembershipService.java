package cz.cvut.fel.bupro.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.MembershipRepository;
import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Membership;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.ProjectCourse;
import cz.cvut.fel.bupro.model.SemesterCode;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.kos.KosClient;

@Service
public class MembershipService {

	@Autowired
	private MembershipRepository membershipRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private KosClient kos;

	@Transactional
	public Membership getMembership(Long projectId, Long userId) {
		Project project = projectRepository.findOne(projectId);
		User user = userRepository.findOne(userId);
		return membershipRepository.findByProjectAndUser(project, user);
	}

	@Transactional
	public List<Membership> getMemberships(User user, ProjectCourse course) {
		return getMemberships(user, course, new SemesterCode(kos.getSemester().getCode()));
	}

	@Transactional
	public List<Membership> getMemberships(User user, ProjectCourse course, SemesterCode semesterCode) {
		return membershipRepository.findByUserAndCourse(user, course);
	}

	@Transactional
	public void deleteMembership(Collection<Membership> memberships) {
		membershipRepository.delete(memberships);
	}

	@Transactional
	public void deleteMembership(Membership membership) {
		membershipRepository.delete(membership);
	}
}
