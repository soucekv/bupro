package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class TagGroup extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -7802058212205883532L;

	@Column(unique = true, nullable = false)
	@NotEmpty
	private String name;

	@OneToMany(mappedBy = "group", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Tag> tags = new HashSet<Tag>();

	public TagGroup() {
	}

	public TagGroup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public void add(Tag tag) {
		tag.setGroup(this);
		getTags().add(tag);
	}

	public void addAll(Collection<Tag> tags) {
		for (Tag tag : tags) {
			tag.setGroup(this);
		}
		getTags().addAll(tags);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + ((getName() == null) ? 0 : getName().hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TagGroup)) {
			return false;
		}
		Tag other = (Tag) obj;
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

}
