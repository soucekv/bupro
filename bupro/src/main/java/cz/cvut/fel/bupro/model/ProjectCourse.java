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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ProjectCourse)) {
			return false;
		}
		ProjectCourse other = (ProjectCourse) obj;
		if (getCode() == null) {
			if (other.getCode() != null) {
				return false;
			}
		} else if (!getCode().equals(other.getCode())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getName();
	}

}
