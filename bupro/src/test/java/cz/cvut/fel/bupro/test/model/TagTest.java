package cz.cvut.fel.bupro.test.model;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.TagRepository;
import cz.cvut.fel.bupro.model.Tag;
import cz.cvut.fel.bupro.test.configuration.TestJpaConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestJpaConfig.class})
public class TagTest {

	@Autowired
	private TagRepository tagRepository;

	@Test
	@Transactional
	public void shouldPersistTag() {
		Tag tag = new Tag();
		tag.setName("Java");
		tagRepository.saveAndFlush(tag);

		assert tag.getId() != null;
	}

	@Test(expected = ConstraintViolationException.class)
	@Transactional
	public void shouldPreventNullName() {
		Tag tag = new Tag();

		tagRepository.saveAndFlush(tag);
	}

	@Test(expected = PersistenceException.class)
	@Transactional
	public void shouldPreventDuplicateName() {
		Tag tag1 = new Tag();
		tag1.setName("Java");
		tagRepository.save(tag1);

		Tag tag2 = new Tag();
		tag2.setName("Java");

		assert tag1.getName().equals(tag2.getName());

		tagRepository.save(tag2);
	}

}
