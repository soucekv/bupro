package cz.cvut.fel.bupro.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.TagRepository;
import cz.cvut.fel.bupro.model.Tag;

@Service
public class TagService {

	@Autowired
	private TagRepository tagRepository;

	@Transactional
	public List<Tag> getAllTags() {
		return tagRepository.findAll();
	}

	@Transactional
	public Set<Tag> refresh(Set<Tag> tags) {
		if (tags.isEmpty()) {
			return tags;
		}
		Set<Tag> set = new HashSet<Tag>();
		for (Tag tag : tags) {
			if (tag == null) {
				continue;
			}
			if (tag.getId() == null) {
				set.add(tag);
				continue;
			}
			set.add(tagRepository.findOne(tag.getId()));
		}
		return set;
	}

}
