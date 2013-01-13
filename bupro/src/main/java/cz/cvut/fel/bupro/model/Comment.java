package cz.cvut.fel.bupro.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Comment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 4002491472087708215L;

	@Embedded
	private Authorship authorship;

	private String title;
	private String text;

	public Authorship getAuthorship() {
		return authorship;
	}

	public void setAuthorship(Authorship authorship) {
		this.authorship = authorship;
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

}
