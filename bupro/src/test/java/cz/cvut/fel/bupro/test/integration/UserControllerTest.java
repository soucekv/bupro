package cz.cvut.fel.bupro.test.integration;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Contains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.test.configuration.AbstractControllerTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest extends AbstractControllerTest {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	private User getUserInstance() {
		List<User> list = userRepository.findAll();
		for(User user : list) {
			if (user.getUsername().equals("admin")) {
				continue; //do not use admin for testing
			}
			return user;
		}
		return list.iterator().next();
	}

	@Test
	@Transactional
	public void testGetUserList() throws Exception {
		log.info("testGetUserList");
		User user = getUserInstance();
		mockMvc.perform(get("/user/list"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("users"))
		.andExpect(view().name("user-list"))
		.andExpect(content().string(new Contains(user.getFullName())))
		.andExpect(content().string(new Contains("/user/view/" + user.getId())));
	}

	@Test
	@Transactional
	public void testGetUser() throws Exception {
		log.info("testGetUser");
		User user = getUserInstance();
		mockMvc.perform(get("/user/view/" + user.getId()))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("user"))
		.andExpect(model().attribute("user", user))
		.andExpect(view().name("user-view"))
		.andExpect(content().string(new Contains(user.getFullName())));
	}

}
