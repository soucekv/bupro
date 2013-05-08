package cz.cvut.fel.bupro.test.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.security.SecurityService;
import cz.cvut.fel.bupro.security.SpringSecurityService;

@Configuration
@Import({TestJpaConfig.class})
@ComponentScan({ "cz.cvut.fel.bupro.controller", "cz.cvut.fel.bupro.service", "cz.cvut.fel.bupro.test.mock" })
public class CommonTestConfig {

	@Bean
	public SecurityService securityService() {
		SpringSecurityService securityService = Mockito.mock(SpringSecurityService.class);
		User user = new User();
		user.setId(3L);
		user.setUsername("test");
		user.setEmail("test@email");
		user.setFirstName("Test");
		user.setLastName("Test");
		Mockito.stub(securityService.getCurrentUser()).toReturn(user);
		return securityService;
	}

}
