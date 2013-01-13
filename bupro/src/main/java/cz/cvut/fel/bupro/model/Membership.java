package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class Membership extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 5731617459882117644L;

	@ManyToOne(optional = false)
	private User user;

	@ManyToOne(optional = false)
	private Project project;

	@Column(nullable = false)
	private Timestamp created;
	@Column(nullable = false)
	private Timestamp changed;

	@Enumerated
	@Column(nullable = false)
	private MembershipState membershipState;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getChanged() {
		return changed;
	}

	public void setChanged(Timestamp changed) {
		this.changed = changed;
	}

	public MembershipState getMembershipState() {
		return membershipState;
	}

	public void setMembershipState(MembershipState membershipState) {
		this.membershipState = membershipState;
	}

}
