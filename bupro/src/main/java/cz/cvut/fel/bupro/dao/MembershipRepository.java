package cz.cvut.fel.bupro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cz.cvut.fel.bupro.model.Membership;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.ProjectCourse;
import cz.cvut.fel.bupro.model.User;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
	List<Membership> findByUser(User user);
	@Query("select m from Membership m where m.user = :user and m.project.course = :course")
	List<Membership> findByUserAndCourse(@Param("user") User user, @Param("course") ProjectCourse course);
	Membership findByProjectAndUser(Project project, User user);
}
