package cz.cvut.fel.bupro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fel.bupro.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
