package cz.cvut.fel.bupro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class ContextConfig {
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
