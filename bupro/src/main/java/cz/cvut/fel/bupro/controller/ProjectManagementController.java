package cz.cvut.fel.bupro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.service.ProjectService;

@Controller
public class ProjectManagementController {

	@Autowired
	private ProjectService projectService;
	
	@ModelAttribute("projectList")
	public List<Project> getProjectList() {
		return projectService.getAllProjects();
	}
	
	@RequestMapping({"/","/project"})
	public String showProjectList() {
		return "project";
	}
}
