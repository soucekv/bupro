package cz.cvut.fel.bupro.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class CommentableEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 5905065623505879738L;

	@OneToMany(cascade={CascadeType.ALL})
	private List<Comment> comments = new LinkedList<Comment>();

	public void add(Comment comment) {
		comments.add(comment);
	}

	public List<Comment> getComments() {
		return comments;
	}

}
