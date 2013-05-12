package cz.cvut.fel.bupro.config;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private final Log log = LogFactory.getLog(getClass());

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
				String value = String.valueOf(e.getValue());
				if (value.startsWith(":")) {
					value = value.substring(1);
					log.info("Application property '" + e.getKey() + "' value is configured as env variable '" + value + "'");
					String env = System.getenv(value);
					if (env == null) {
						env = "";
						log.error("Application property '" + e.getKey() + "' not found using environment key '" + value + "'");
					}
					mail.put(e.getKey(), env);
				} else {
					if (value == null || value.trim().isEmpty()) {
						log.warn("Application property '" + e.getKey() + " has empty value");
					}
					mail.put(e.getKey(), e.getValue());
				}
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
