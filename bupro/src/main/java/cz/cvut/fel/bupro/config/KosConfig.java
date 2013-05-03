package cz.cvut.fel.bupro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import cz.cvut.fel.kos.Configuration.Authentication;
import cz.cvut.fel.kos.KosClient;
import cz.cvut.fel.kos.KosClientFactory;
import cz.cvut.fel.kos.impl.DefaultKosClientConfiguration;

@Configuration
@Import(DefaultKosClientConfiguration.class)
@PropertySource({ "classpath:cfg/kos.properties" })
public class KosConfig {

	@Value("${kos.uri}")
	private String uri;
	@Value("${kos.auth}")
	private String auth;
	@Value("${kos.auth.username}")
	private String username;
	@Value("${kos.auth.password}")
	private String password;

	@Autowired
	private KosClientFactory kosClientFactory;

	public cz.cvut.fel.kos.Configuration kosConfiguration() {
		Authentication authentication = Authentication.valueOf(String.valueOf(auth).toUpperCase());
		return new cz.cvut.fel.kos.Configuration(uri, username, password, authentication);
	}

	@Bean
	public KosClient kosClient() {
		return kosClientFactory.createInstance(kosConfiguration());
	}

}
