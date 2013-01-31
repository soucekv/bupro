package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Tag extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -3709930348866558631L;

	@Column(unique = true, nullable = false)
	private String name;

	@ManyToMany
	private Set<Project> projects;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return getName();
	}
}
