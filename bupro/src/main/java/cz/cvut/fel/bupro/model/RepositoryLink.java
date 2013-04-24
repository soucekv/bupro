package cz.cvut.fel.bupro.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import cz.cvut.fel.reposapi.ServiceProvider;

@Embeddable
public class RepositoryLink implements Serializable {
	private static final long serialVersionUID = 319929691979763814L;

	@Enumerated
	private ServiceProvider repositoryProvider;
	private String repositoryUser;
	private String repositoryName;

	public ServiceProvider getRepositoryProvider() {
		return repositoryProvider;
	}

	public void setRepositoryProvider(ServiceProvider repositoryProvider) {
		this.repositoryProvider = repositoryProvider;
	}

	public String getRepositoryUser() {
		return repositoryUser;
	}

	public void setRepositoryUser(String repositoryUser) {
		this.repositoryUser = repositoryUser;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	@Override
	public String toString() {
		return "RepositoryLink [repositoryProvider=" + repositoryProvider + ", repositoryUser=" + repositoryUser + ", repositoryName=" + repositoryName + "]";
	}

}
