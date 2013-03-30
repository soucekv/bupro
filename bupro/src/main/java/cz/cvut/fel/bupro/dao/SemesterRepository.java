package cz.cvut.fel.bupro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fel.bupro.model.Semester;

public interface SemesterRepository extends JpaRepository<Semester, Long> {

}
