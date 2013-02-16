package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name = "bupro_user")
public class User extends CommentableEntity implements Serializable {
	private static final long serialVersionUID = -5431213892674807472L;

	@Column(unique = true, nullable = false)
	private String username;
	private String firstName;
	private String lastName;
	@Column(unique = true, nullable = false)
	private String email;

	@OneToMany
	private Set<Membership> memberships;

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
}
