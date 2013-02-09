package cz.cvut.fel.bupro.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.model.Project;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public List<Project> getAllProjects() {
		Project p1 = new Project();
		p1.setName("Test 1");
		Project p2 = new Project();
		p2.setName("Test 2");		
		return Arrays.asList(p1, p2);
	}
	
}
