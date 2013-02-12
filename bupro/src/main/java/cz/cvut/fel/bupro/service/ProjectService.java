package cz.cvut.fel.bupro.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.model.Authorship;
import cz.cvut.fel.bupro.model.Comment;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public List<Project> getAllProjects() {
		User u = new User();
		u.setFirstName("Karel");
		u.setLastName("Vomacka");
		Project p1 = new Project();
		p1.setId(0L);
		p1.setName("Test 1");
		p1.setAuthorship(new Authorship(u));
		Comment comment = new Comment();
		comment.setAuthorship(new Authorship(u));
		comment.setTitle("Some extra notes");
		comment.setText("Dont forget to get extra reward for this project");
		p1.add(comment);
		u = new User();
		u.setFirstName("Venca");
		u.setLastName("Rohlik");
		Project p2 = new Project();
		p2.setId(1L);
		p2.setName("Test 2");
		p2.setAuthorship(new Authorship(u));
		return Arrays.asList(p1, p2);
	}

	public Project getProject(Long id) {
		int index = id.intValue();
		return getAllProjects().get(index);
	}
	
}
