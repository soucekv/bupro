package cz.cvut.fel.bupro.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cz.cvut.fel.bupro.model.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	User findByUsername(String username);
	List<User> findByFirstNameLikeOrLastNameLike(String firstName, String lastName, Pageable pageable);
}
