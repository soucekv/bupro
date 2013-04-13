package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "bupro_user")
public class User extends CommentableEntity implements UserDetails, Serializable {
	private static final long serialVersionUID = -5431213892674807472L;

	@Column(unique = true, nullable = false)
	private String username;
	private String firstName;
	private String lastName;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;

	@OneToMany(mappedBy = "user")
	private Set<Membership> memberships = new HashSet<Membership>();

	@OneToMany(mappedBy = "user")
	private Set<Enrolment> enrolments = new HashSet<Enrolment>();

	@ManyToMany
	@JoinTable(name = "bupro_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(Set<Membership> memberships) {
		this.memberships = memberships;
	}

	public Set<Enrolment> getEnrolments() {
		return enrolments;
	}

	public void setEnrolments(Set<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Collection<Role> getAuthorities() {
		return getRoles();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	private Set<Subject> getSubjects(EnrolmentType enrolmentType) {
		Set<Subject> set = new HashSet<Subject>();
		for (Enrolment enrolment : getEnrolments()) {
			if (enrolment.getEnrolmentType() == enrolmentType) {
				set.add(enrolment.getSubject());
			}
		}
		return set;
	}

	public Set<Subject> getTeachedSubjects() {
		return getSubjects(EnrolmentType.TEACHER);
	}

	public Set<Subject> getStudiedSubjects() {
		return getSubjects(EnrolmentType.STUDENT);
	}

	public boolean isTeacher() {
		return !getTeachedSubjects().isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (getUsername() == null) {
			if (other.getUsername() != null) {
				return false;
			}
		} else if (!getUsername().equals(other.getUsername())) {
			return false;
		}
		return true;
	}

}
