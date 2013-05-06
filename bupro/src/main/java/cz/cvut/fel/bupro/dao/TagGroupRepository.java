package cz.cvut.fel.bupro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fel.bupro.model.TagGroup;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {

	TagGroup findByName(String string);
}
