package cz.cvut.fel.bupro.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		return Collections.emptyList();
	}

	@RequestMapping({ "/subject/list" })
	public String showList() {
		return "subject-list";
	}

}
