package cz.cvut.fel.bupro.model;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePassword {

	private Long id;
	@NotEmpty
	private String oldPassword;
	@NotEmpty
	private String newPassword;
	@NotEmpty
	private String reNewPassword;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReNewPassword() {
		return reNewPassword;
	}

	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}

	public boolean isCorrect() {
		return String.valueOf(getNewPassword()).equals(getReNewPassword());
	}
}
