package cz.cvut.fel.bupro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
	List<Project> findByOwner(User user);
}
