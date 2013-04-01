package cz.cvut.fel.bupro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fel.bupro.model.Membership;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
	Membership findByProjectAndUser(Project project, User user);
}
