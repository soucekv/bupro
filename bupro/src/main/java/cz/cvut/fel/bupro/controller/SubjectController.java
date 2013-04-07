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

import cz.cvut.fel.bupro.model.Subject;
import cz.cvut.fel.bupro.service.SubjectService;

@Controller
public class SubjectController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private SubjectService subjectService;

	@ModelAttribute("subjectList")
	public List<Subject> getAllSubjects() {
		return subjectService.getAllSubjects();
	}

	@RequestMapping({ "/subject/list" })
	public String showList() {
		return "subject-list";
	}

	@RequestMapping({ "/subject/view/{id}" })
	public String showDetail(Model model, Locale locale, @PathVariable Long id) {
		Subject subject = subjectService.getSubject(id);
		model.addAttribute("subject", subject);
		return "subject-view";
	}

	@RequestMapping({ "/subject/edit/{id}" })
	public String editDetail(Model model, Locale locale, @PathVariable Long id) {
		Subject subject = subjectService.getSubject(id);
		model.addAttribute("subject", subject);
		return "subject-edit";
	}

	@RequestMapping({ "/subject/create" })
	public String create(Model model, Locale locale) {
		log.trace("SubjectController.createProject()");
		model.addAttribute("subject", new Subject());
		return "subject-edit";
	}

	@RequestMapping({ "/subject/save" })
	public String save(@Validated Subject subject, BindingResult errors, Map<String, Object> model) {
		subject = subjectService.save(subject);
		log.info("Project saved " + subject);
		return "redirect:/subject/list/";
	}
}
