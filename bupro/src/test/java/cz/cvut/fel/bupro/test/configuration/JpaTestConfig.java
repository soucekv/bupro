package cz.cvut.fel.bupro.test.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"cz.cvut.fel.bupro.dao"})
@EnableTransactionManagement
public class JpaTestConfig {
	private static final String DB_NAME = "testdb";

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		properties.put("hibernate.connection.username", "sa");
		properties.put("hibernate.connection.password", "");
		properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan(new String[] { "cz.cvut.fel.bupro.model" });
		entityManagerFactory.setPersistenceProvider(new HibernatePersistence());
		entityManagerFactory.setJpaProperties(properties);
		entityManagerFactory.setPersistenceUnitName(DB_NAME);
		entityManagerFactory.afterPropertiesSet();
		return entityManagerFactory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
		transactionManager.setDataSource(dataSource());
		transactionManager.setJpaDialect(new HibernateJpaDialect());
		return transactionManager;
	}

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		return embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.HSQL).setName(DB_NAME).build();
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

}
