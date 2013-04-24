package cz.cvut.fel.bupro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cz.cvut.fel.reposapi.DefaultRepositoryClientFactory;
import cz.cvut.fel.reposapi.RepositoryClientFactory;

@Configuration
public class ReposapiConfig {

	@Bean
	public RepositoryClientFactory repositoryClientFactory() {
		return DefaultRepositoryClientFactory.getInstance();
	}
}
