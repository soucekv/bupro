package cz.cvut.fel.bupro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.SemesterRepository;
import cz.cvut.fel.bupro.model.Semester;

@Service
public class SemesterService {

	@Autowired
	private SemesterRepository semesterRepository;

	@Transactional
	public List<Semester> getAllSemesters() {
		return semesterRepository.findAll();
	}
}
