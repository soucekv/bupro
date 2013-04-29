package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import cz.cvut.fel.bupro.TimeUtils;

@Entity
public class Project extends CommentableEntity implements Serializable {
	private static final long serialVersionUID = -4565743549559354326L;

	@Column(unique = true, nullable = false)
	@NotEmpty
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
	private ProjectCourse course;

	@Min(value = 1)
	private int capacity = 1;

	private boolean autoApprove;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(name = "startSemester")) })
	private SemesterCode startSemester;
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(name = "endSemester")) })
	private SemesterCode endSemester;

	@Embedded
	private RepositoryLink repository = new RepositoryLink();

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

	public ProjectCourse getCourse() {
		return course;
	}

	public void setCourse(ProjectCourse course) {
		this.course = course;
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

	public SemesterCode getStartSemester() {
		return startSemester;
	}

	public void setStartSemester(SemesterCode startSemester) {
		this.startSemester = startSemester;
	}

	public SemesterCode getEndSemester() {
		return endSemester;
	}

	public void setEndSemester(SemesterCode endSemester) {
		this.endSemester = endSemester;
	}

	public RepositoryLink getRepository() {
		return repository;
	}

	public void setRepository(RepositoryLink repository) {
		this.repository = repository;
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

	/**
	 * 
	 * @return list of users with active membership
	 */
	public List<User> getMembers() {
		List<Membership> memberships = getMemberships(MembershipState.APPROVED);
		List<User> users = new LinkedList<User>();
		for (Membership membership : memberships) {
			users.add(membership.getUser());
		}
		return users;
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

	public boolean hasMember(User user) {
		for (Membership membership : getMemberships()) {
			if (membership.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return getClass().getName() + " [id=" + getId() + ", name=" + name + ", owner=" + owner + ", tags=" + getTags() + "]";
	}

}
