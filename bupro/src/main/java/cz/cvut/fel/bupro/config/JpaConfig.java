package cz.cvut.fel.bupro.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfig {
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());		
		entityManagerFactory.setPackagesToScan(new String[] { "cz.cvut.fel.bupro.model" });
		entityManagerFactory.setPersistenceProvider(new HibernatePersistence());
		entityManagerFactory.setJpaProperties(properties);
		entityManagerFactory.setPersistenceUnitName("bupro");
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
		String username = "bupro";
		String password = "bupro_devel";
		String path = "bupro";
	
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/" + path);
		dataSource.setUsername(username);
		dataSource.setPassword(password);	
		return dataSource;
	}	
	
//	@Bean
//	public DataSource datasource() {
//		EmbeddedDatabaseFactoryBean bean = new EmbeddedDatabaseFactoryBean();
//		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//		databasePopulator.addScript(new ClassPathResource("hibernate/config/java/schema.sql"));
//		bean.setDatabasePopulator(databasePopulator);
//		bean.afterPropertiesSet(); // necessary because
//									// EmbeddedDatabaseFactoryBean is a
//									// FactoryBean
//		return bean.getObject();
//	}
	
	
}	