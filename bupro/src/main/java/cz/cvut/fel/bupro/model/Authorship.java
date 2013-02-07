package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class Authorship implements Serializable {
	private static final long serialVersionUID = -6239486295089657373L;

	@ManyToOne(optional = false)
	private User author;
	@Column(nullable = false)
	private Timestamp creationTime;

	public Authorship() {
		// Default constructor JPA required
	}

	public Authorship(User author) {
		this(author, new Timestamp(new Date().getTime()));
	}

	public Authorship(User author, Timestamp creationTime) {
		this.author = author;
		this.creationTime = creationTime;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public String toString() {
		return "Authorship [creator=" + author + ", creationTime=" + creationTime + "]";
	}
}
