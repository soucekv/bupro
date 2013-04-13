package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "bupro_role")
public class Role extends BaseEntity implements GrantedAuthority, Serializable {
	private static final long serialVersionUID = -983927348937047953L;

	@Column(unique = true, nullable = false)
	private String authority;

	@ManyToMany
	private Set<User> users = new HashSet<User>();

	public Role() {
	}

	public Role(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<User> getUsers() {
		return users;
	}

}
