package cz.cvut.fel.bupro.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping({"/","/project-list"})
	public String showProjectList() {
		return "project-list";
	}

	@RequestMapping({"/project/{id}"})
	public String showProjectDetail(Model model, Locale locale, @PathVariable Long id) {
		Project project = projectService.getProject(id);
		model.addAttribute("project", project);
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", locale);
		model.addAttribute("creationTime", format.format(project.getAuthorship().getCreationTime()));
		return "project";
	}

}
