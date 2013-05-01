package cz.cvut.fel.bupro.model;

import org.hibernate.validator.constraints.NotBlank;

public class Registration {

	@NotBlank
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
