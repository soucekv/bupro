package cz.cvut.fel.bupro.config;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class ContextConfig {

	@Autowired
	@Qualifier("application.properties")
	private Properties properties;

	@Bean
	@Qualifier("application.properties")
	public Properties loadApplicationProperties() throws IOException {
		Resource resource = new ClassPathResource("/cfg/application.properties");
		return PropertiesLoaderUtils.loadProperties(resource);
	}

	@Bean
	@Qualifier(Qualifiers.EMAIL)
	public Properties emailConfiguration() throws IOException {
		Properties mail = new Properties();
		for (Map.Entry<Object, Object> e : properties.entrySet()) {
			if (String.valueOf(e.getKey()).startsWith("mail")) {
				mail.put(e.getKey(), e.getValue());
			}
		}
		return mail;
	}

	@Bean
	@Qualifier(Qualifiers.EXPIRATION)
	public int expirationLimit() {
		String value = properties.getProperty("expiration.notify.days.before", "10");
		return Integer.parseInt(value);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
