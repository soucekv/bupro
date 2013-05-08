package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "bupro_user")
public class User extends CommentableEntity implements UserDetails, Serializable {
	private static final long serialVersionUID = -5431213892674807472L;

	@Column(unique = true, nullable = false)
	@NotEmpty
	private String username;
	private String titlePre;
	private String firstName;
	private String lastName;
	private String titlePost;
	@Column(unique = true, nullable = false)
	@Email
	private String email;
	@Column(nullable = false)
	@NotEmpty
	private String password;
	@Column(length = 2048)
	private String aboutme;

	private String lang = Locale.ENGLISH.getLanguage();

	@OneToMany(mappedBy = "user")
	private Set<Membership> memberships = new HashSet<Membership>();

	@ManyToMany
	@JoinTable(name = "bupro_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();

	public String getUsername() {
		return username;
	}

	public String getTitlePre() {
		return titlePre;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTitlePre(String titlePre) {
		this.titlePre = titlePre;
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

	public String getTitlePost() {
		return titlePost;
	}

	public void setTitlePost(String titlePost) {
		this.titlePost = titlePost;
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

	public String getAboutme() {
		return aboutme;
	}

	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
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

	public String getFullName() {
		StringBuilder sb = new StringBuilder();
		if (getTitlePre() != null && !getTitlePre().trim().isEmpty()) {
			sb.append(getTitlePre());
			sb.append(' ');
		}
		sb.append(getFirstName());
		if (getLastName() != null) {
			sb.append(' ');
			sb.append(getLastName());
		}
		if (getTitlePost() != null && !getTitlePost().trim().isEmpty()) {
			sb.append(' ');
			sb.append(getTitlePost());
		}
		return sb.toString();
	}

	public boolean isTeacher() {
		for (Role role : getRoles()) {
			if ("ROLE_TEACHER".equals(role.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates new {@link User} instance and sets properties using string
	 * containing full name
	 * 
	 * @param fullName
	 * @return
	 */
	public static User parseFullName(String fullName) {
		String[] tokens = fullName.split("\\s+");
		User user = new User();
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if (token.endsWith(".")) {
				if (user.getFirstName() == null) {
					if (user.getTitlePre() == null) {
						user.setTitlePre(token);
					} else {
						user.setTitlePre(user.getTitlePre() + " " + token);
					}
				} else {
					if (user.getTitlePost() == null) {
						user.setTitlePost(token);
					} else {
						user.setTitlePost(user.getTitlePost() + " " + token);
					}
				}
			} else {
				if (user.getFirstName() == null) {
					user.setFirstName(token);
				} else if (user.getLastName() == null) {
					user.setLastName(token);
				}
			}
		}
		return user;
	}
}
