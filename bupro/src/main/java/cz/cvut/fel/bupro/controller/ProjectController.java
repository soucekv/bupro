package cz.cvut.fel.bupro.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.service.LoginService;
import cz.cvut.fel.bupro.service.ProjectService;
import cz.cvut.fel.bupro.service.SubjectService;

@Controller
public class ProjectController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ProjectService projectService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private LoginService loginService;

	@ModelAttribute("user")
	@Transactional
	public User getLoggedInUser() {
		User user = loginService.getLoggedInUser();
		user.getEnrolments().size(); // force fetch
		return user;
	}

	@ModelAttribute("projectList")
	public List<Project> getProjectList() {
		return projectService.getAllProjects();
	}

	@RequestMapping({ "*", "/project/list" })
	public String showProjectList() {
		return "project-list";
	}

	@RequestMapping({ "/project/view/{id}" })
	@Transactional
	public String showProjectDetail(Model model, Locale locale, @PathVariable Long id) {
		Project project = projectService.getProject(id);
		project.getMemberships().size(); // force fetch
		project.getComments().size(); // force fetch
		project.getTags().size(); // force fetch
		model.addAttribute("project", project);
		return "project-view";
	}

	@RequestMapping({ "/project/edit/{id}" })
	@Transactional
	public String editProjectDetail(Model model, Locale locale, @PathVariable Long id) {
		Project project = projectService.getProject(id);
		model.addAttribute("project", project);
		model.addAttribute("subjectList", project.getOwner().getTeachedSubjects());
		return "project-edit";
	}

	@RequestMapping({ "/project/create" })
	@Transactional
	public String createProject(Model model, Locale locale) {
		log.trace("ProjectManagementController.createProject()");
		User user = loginService.getLoggedInUser();
		Project project = new Project();
		model.addAttribute("project", project);
		model.addAttribute("subjectList", user.getTeachedSubjects());
		return "project-edit";
	}

	@RequestMapping({ "/project/save" })
	@Transactional
	public String saveProject(@Validated Project project, BindingResult errors, Map<String, Object> model) {
		User user = loginService.getLoggedInUser();
		if (project.getOwner() == null) {
			project.setOwner(user);
		}
		if (project.getOwner().equals(user)) {
			project = projectService.save(project);
			log.info("Project saved " + project);
		} else {
			log.error("Can't save " + project + " user " + user + " is not owner");
		}
		return "redirect:/project/view/" + project.getId();
	}
}
