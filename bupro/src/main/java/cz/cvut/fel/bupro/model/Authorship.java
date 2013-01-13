package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class Authorship implements Serializable {
	private static final long serialVersionUID = -6239486295089657373L;

	@ManyToOne(optional = false)
	private User creator;
	@Column(nullable = false)
	private Timestamp creationTime;

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public String toString() {
		return "Authorship [creator=" + creator + ", creationTime="
				+ creationTime + "]";
	}
}
