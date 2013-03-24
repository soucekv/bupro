package cz.cvut.fel.bupro.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "subject_id" }) })
public class Enrolment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 2190428849021322050L;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private User user;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Subject subject;

	@Enumerated
	private EnrolmentType enrolmentType = EnrolmentType.STUDENT;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public EnrolmentType getEnrolmentType() {
		return enrolmentType;
	}

	public void setEnrolmentType(EnrolmentType enrolmentType) {
		this.enrolmentType = enrolmentType;
	}

}
