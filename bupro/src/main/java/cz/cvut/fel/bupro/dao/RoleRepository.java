package cz.cvut.fel.bupro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fel.bupro.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByAuthority(String authority);
}
