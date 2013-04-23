package cz.cvut.fel.bupro.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "course")
public class ProjectCourse extends BaseEntity {
	private static final long serialVersionUID = -5477454797474559891L;

	@Column(nullable = false, unique = true)
	@NotEmpty
	private String code;
	@OneToMany(mappedBy = "course")
	private Set<Project> projects = new HashSet<Project>();

	@Transient
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public String getName() {
		if (name == null) {
			return getCode();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
