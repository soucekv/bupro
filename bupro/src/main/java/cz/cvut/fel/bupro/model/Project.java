package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project extends CommentableEntity implements Serializable {
	private static final long serialVersionUID = -4565743549559354326L;

	@Column(unique = true, nullable = false)
	private String name;
	private String description;

	@Embedded
	private Authorship authorship;

	@OneToMany
	private List<Membership> memberships = new LinkedList<Membership>();

	@ManyToMany
	private Set<Tag> tags = new HashSet<Tag>();

	@ManyToOne
	private Subject subject;

	private boolean autoApprove;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Authorship getAuthorship() {
		return authorship;
	}

	public void setAuthorship(Authorship authorship) {
		this.authorship = authorship;
	}

	public List<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public boolean isAutoApprove() {
		return autoApprove;
	}

	public void setAutoApprove(boolean autoApprove) {
		this.autoApprove = autoApprove;
	}

}
