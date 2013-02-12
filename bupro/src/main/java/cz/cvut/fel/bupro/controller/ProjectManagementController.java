package cz.cvut.fel.bupro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping({"/","/project-list"})
	public String showProjectList() {
		return "project-list";
	}

	@RequestMapping({"/","/project"})
	public String showProjectDetail(Model model, @RequestParam("id") Long id) {
		model.addAttribute("project", projectService.getProject(id));
		return "project";
	}

}
