package cz.cvut.fel.bupro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.SubjectRepository;
import cz.cvut.fel.bupro.model.Subject;

@Service
public class SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;

	@Transactional
	public List<Subject> getAllSubjects() {
		return subjectRepository.findAll();
	}

	@Transactional
	public Subject getSubject(Long id) {
		Subject subject = subjectRepository.findOne(id);
		subject.getProjects().size();
		subject.getEnrolments().size();
		return subject;
	}

	@Transactional
	public Subject save(Subject subject) {
		return subjectRepository.save(subject);
	}
}
