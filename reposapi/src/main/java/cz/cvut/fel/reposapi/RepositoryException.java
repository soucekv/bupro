package cz.cvut.fel.reposapi;

import java.io.Serializable;

public class RepositoryException extends Exception implements Serializable {
	private static final long serialVersionUID = -4047539200263312207L;

	public RepositoryException() {
		super();
	}

	public RepositoryException(String messgae) {
		super(messgae);
	}

	public RepositoryException(Throwable throwable) {
		super(throwable);
	}

	public RepositoryException(String messgae, Throwable throwable) {
		super(messgae, throwable);
	}

}
