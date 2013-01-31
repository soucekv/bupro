package cz.cvut.fel.bupro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fel.bupro.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
