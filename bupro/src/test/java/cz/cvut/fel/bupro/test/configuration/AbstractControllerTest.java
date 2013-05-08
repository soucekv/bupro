package cz.cvut.fel.bupro.test.configuration;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import cz.cvut.fel.bupro.config.ContextConfig;
import cz.cvut.fel.bupro.config.KosConfig;
import cz.cvut.fel.bupro.config.MvcConfig;
import cz.cvut.fel.bupro.config.ReposapiConfig;
import cz.cvut.fel.bupro.config.SecurityConfig;

@WebAppConfiguration
@ContextConfiguration(classes = {IntegrationTestConfig.class, ContextConfig.class, MvcConfig.class, KosConfig.class, SecurityConfig.class, ReposapiConfig.class})
public class AbstractControllerTest {

}
