package cz.cvut.fel.bupro.test.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.cvut.fel.bupro.dao.TagRepository;
import cz.cvut.fel.bupro.model.Tag;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
"classpath:testContext.xml"
})
public class TagTest {
	
	@Autowired
	private TagRepository tagRepository;
	
	@Test
	public void shouldPersistTag() {
		Tag tag = new Tag();
		tag.setName("Java");
		tagRepository.saveAndFlush(tag);

		assert tag.getId() != null;
	}

	@Test(expected=DataIntegrityViolationException.class)
	public void shouldPreventNullName() {
		Tag tag = new Tag();
		
		tagRepository.saveAndFlush(tag);
	}

}
