package cz.cvut.fel.bupro.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Semester extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -375074470709318756L;

	@Column(nullable = false, unique = true)
	private String code; //KOS internal representation
	
	@Column(nullable = false, unique = true)
	private String name;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
