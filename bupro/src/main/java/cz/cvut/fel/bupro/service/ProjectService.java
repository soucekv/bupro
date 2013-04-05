package cz.cvut.fel.bupro.service;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.TimeUtils;
import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.filter.FilterSpecification;
import cz.cvut.fel.bupro.filter.Filterable;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

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

	public Page<Project> getProjects(Pageable pageable, Filterable filterable) {
		Specification<Project> spec = createSpecification(filterable);
		if (spec == null) {
			return projectRepository.findAll(pageable);
		}
		return projectRepository.findAll(spec, pageable);
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

	private static Specifications<Project> createSingleSpec(Specifications<Project> spec, Map<String, String> map, FilterKey key) {
		Specification<Project> newSpec = createSingleSpec(map, key);
		if (newSpec == null) {
			return null;
		}
		if (spec == null) {
			return Specifications.where(newSpec);
		}
		return spec.and(newSpec);
	}

	private static Specification<Project> createSingleSpec(Map<String, String> map, FilterKey key) {
		final String value = map.get(key.toString());
		if (value != null) {
			if (key == FilterKey.USER) {
				return new Specification<Project>() {
					public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						Join<Project, User> join = root.<Project, User>join("owner");
						String[] name = value.trim().split("\\s");
						if (name.length < 2) {
							return cb.or(cb.like(join.<String>get("firstName"), value + "%"), cb.like(join.<String>get("lastName"), value + "%"));
						}
						return cb.or(cb.like(join.<String>get("firstName"), name[0] + "%"), cb.like(join.<String>get("lastName"), name[1] + "%"));
					}
				};
			}
			return new FilterSpecification<Project>(key.toString(), value);
		}
		return null;
	}

	private static Specification<Project> createSpecification(Filterable filterable) {
		return createSpecification(filterable.getFilter());
	}

	private static Specification<Project> createSpecification(Map<String, String> map) {
		Specifications<Project> specifications = null;
		specifications = createSingleSpec(specifications, map, FilterKey.NAME);
		specifications = createSingleSpec(specifications, map, FilterKey.USER);
		return specifications;
	}

	private static enum FilterKey {
		NAME, USER, SUBJECT, SEMESTER, CREATED;
		public String toString() {
			return super.toString().toLowerCase();
		}
	}
}
