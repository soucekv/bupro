package cz.cvut.fel.bupro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cz.cvut.fel.bupro.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	public Tag findByName(String name);

	@Modifying
	@Query("delete Tag t where t.projects is empty")
	public void removeUnusedTags();
}
