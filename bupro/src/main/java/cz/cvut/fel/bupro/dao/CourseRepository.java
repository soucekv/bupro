package cz.cvut.fel.bupro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fel.bupro.model.ProjectCourse;

public interface CourseRepository extends JpaRepository<ProjectCourse, Long> {

}
