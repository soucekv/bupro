package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import cz.cvut.fel.bupro.TimeUtils;

@Entity
public class Comment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 4002491472087708215L;

	@ManyToOne(optional = false)
	private User user;
	@Column(nullable = false)
	private Timestamp creationTime;

	@Column(nullable = false)
	private String title;
	@Column(nullable = false, length = 2048)
	private String text;
	
	public Comment() {
		creationTime = TimeUtils.createCurrentTimestamp();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Timestamp getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
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
