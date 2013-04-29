package cz.cvut.fel.bupro.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import cz.cvut.fel.kos.KosSemesterCode;

@Embeddable
public class SemesterCode implements Serializable {
	private static final long serialVersionUID = -8277662997851132616L;

	@Column(nullable = false)
	@NotNull
	@Pattern(regexp = KosSemesterCode.REGEX_PATTERN)
	private String code;

	@Transient
	private String name;

	public SemesterCode() {
	}

	public SemesterCode(String code) {
		if (!KosSemesterCode.validate(code)) {
			throw new IllegalArgumentException("Invalid code " + code);
		}
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getCode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SemesterCode)) {
			return false;
		}
		SemesterCode other = (SemesterCode) obj;
		if (getCode() == null) {
			if (other.getCode() != null) {
				return false;
			}
		} else if (!getCode().equals(other.getCode())) {
			return false;
		}
		return true;
	}

}
