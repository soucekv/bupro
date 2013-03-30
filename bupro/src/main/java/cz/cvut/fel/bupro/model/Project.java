package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import cz.cvut.fel.bupro.TimeUtils;

@Entity
public class Project extends CommentableEntity implements Serializable {
	private static final long serialVersionUID = -4565743549559354326L;

	@NotEmpty
	@Column(unique = true, nullable = false)
	private String name;
	private String description;

	@ManyToOne(optional = false)
	private User owner;
	@Column(nullable = false)
	private Timestamp creationTime;

	@OneToMany(mappedBy = "project", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Membership> memberships = new LinkedList<Membership>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "project_tag", joinColumns = @JoinColumn(name = "projects_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
	private Set<Tag> tags = new HashSet<Tag>();

	@ManyToOne(cascade = { CascadeType.MERGE })
	private Subject subject;

	@Min(value = 1)
	private int capacity = 1;

	private boolean autoApprove;

	@ManyToOne(fetch = FetchType.EAGER)
	private Semester startSemester;
	@ManyToOne(fetch = FetchType.EAGER)
	private Semester endSemester;

	public Project() {
		creationTime = TimeUtils.createCurrentTimestamp();
	}

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isAutoApprove() {
		return autoApprove;
	}

	public void setAutoApprove(boolean autoApprove) {
		this.autoApprove = autoApprove;
	}

	public Semester getStartSemester() {
		return startSemester;
	}

	public void setStartSemester(Semester startSemester) {
		this.startSemester = startSemester;
	}

	public Semester getEndSemester() {
		return endSemester;
	}

	public void setEndSemester(Semester endSemester) {
		this.endSemester = endSemester;
	}

	public List<Membership> getMemberships(MembershipState membershipState) {
		return getMemberships(EnumSet.of(membershipState));
	}

	public List<Membership> getMemberships(Set<MembershipState> membershipStates) {
		List<Membership> list = new LinkedList<Membership>();
		for (Membership membership : getMemberships()) {
			if (membershipStates.contains(membership.getMembershipState())) {
				list.add(membership);
			}
		}
		return list;
	}

	public int getApprovedCount() {
		return getMemberships(MembershipState.APPROVED).size();
	}

	public int getWaitingApprovalCount() {
		return getMemberships(MembershipState.WAITING_APPROVAL).size();
	}

	public boolean isCapacityFull() {
		return getCapacity() == getApprovedCount();
	}

	@Override
	public String toString() {
		return getClass().getName() + " [id=" + getId() + ", name=" + name + ", owner=" + owner + ", tags=" + getTags() + "]";
	}
}
