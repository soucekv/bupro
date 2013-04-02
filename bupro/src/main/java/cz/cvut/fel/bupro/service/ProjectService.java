package cz.cvut.fel.bupro.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.TimeUtils;
import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.model.Project;

@Service
public class ProjectService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ProjectRepository projectRepository;

	@Transactional
	public List<Project> getAllProjects() {
		log.info("get all projects");
		return projectRepository.findAll();
	}

	public Page<Project> getProjects(Pageable pageable) {
		log.info("get project page " + pageable.getPageNumber() + " size " + pageable.getPageSize());
		return projectRepository.findAll(pageable);
	}

	@Transactional
	public Project getProject(Long id) {
		log.info("get project " + id);
		Project project = projectRepository.findOne(id);
		project.getMemberships().size(); // force fetch
		project.getComments().size(); // force fetch
		project.getTags().size(); // force fetch
		return project;
	}

	@Transactional
	public Project save(Project project) {
		log.info("save project " + ((project.getId() == null) ? "new" : project.getId()));
		if (project.getCreationTime() == null) {
			project.setCreationTime(TimeUtils.createCurrentTimestamp());
		}
		return projectRepository.save(project);
	}

}
