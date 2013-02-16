package cz.cvut.fel.bupro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fel.bupro.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
