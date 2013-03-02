package cz.cvut.fel.bupro.test.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.SubjectRepository;
import cz.cvut.fel.bupro.model.Subject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" })
@Transactional
public class SubjectTest {

	@Autowired
	private SubjectRepository subjectRepository;

	@Test
	public void shouldPersistSubject() {
		Subject subject = new Subject();
		subject.setName("Test Subject");
		subjectRepository.save(subject);
		assert subject.getId() != null;
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void shouldPreventNullName() {
		Subject subject = new Subject();
		subject.setName(null);
		subjectRepository.save(subject);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void shouldPreventDuplicateName() {
		Subject subject1 = new Subject();
		subject1.setName("Test Subject");
		subjectRepository.save(subject1);
		Subject subject2 = new Subject();
		subject2.setName("Test Subject");
		subjectRepository.save(subject2);
	}
}
