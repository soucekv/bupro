package cz.cvut.fel.bupro.model;

import java.util.Collection;

import org.hibernate.validator.constraints.NotEmpty;

public class Email {

	private Long id;

	@NotEmpty
	private String to;
	private String c;
	private String title;
	private String text;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void addAllTo(Collection<? extends User> users) {
		StringBuilder sb = new StringBuilder();
		if (to != null) {
			sb.append(to);
		}
		for (User user : users) {
			if (sb.length() > 0) {
				sb.append(';');
			}
			sb.append(user.getEmail());
		}
		to = sb.toString();
	}

}
