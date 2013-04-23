package cz.cvut.fel.bupro.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.cvut.fel.bupro.model.ProjectCourse;
import cz.cvut.fel.bupro.service.CourseService;

@Controller
public class SubjectController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private CourseService courseService;

	@ModelAttribute("subjectList")
	public List<ProjectCourse> getAllSubjects() {
		return courseService.getProjectCourses();
	}

	@RequestMapping({ "/subject/list" })
	public String showList() {
		return "subject-list";
	}

	@RequestMapping({ "/subject/view/{id}" })
	public String showDetail(Model model, Locale locale, @PathVariable Long id) {
		ProjectCourse course = courseService.getProjectCourse(id);
		model.addAttribute("subject", course);
		return "subject-view";
	}

	@RequestMapping({ "/subject/edit/{id}" })
	public String editDetail(Model model, Locale locale, @PathVariable Long id) {
		ProjectCourse course = courseService.getProjectCourse(id);
		model.addAttribute("subject", course);
		return "subject-edit";
	}

	@RequestMapping({ "/subject/create" })
	public String create(Model model, Locale locale) {
		log.trace("SubjectController.createProject()");
		model.addAttribute("subject", new ProjectCourse());
		return "subject-edit";
	}

	@RequestMapping({ "/subject/save" })
	public String save(@Validated ProjectCourse course, BindingResult errors, Map<String, Object> model) {
		course = courseService.save(course);
		log.info("Project saved " + course);
		return "redirect:/subject/list/";
	}
}
