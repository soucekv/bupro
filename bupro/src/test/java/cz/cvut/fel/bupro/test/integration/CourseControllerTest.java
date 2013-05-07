package cz.cvut.fel.bupro.test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Contains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import cz.cvut.fel.bupro.model.ProjectCourse;
import cz.cvut.fel.bupro.service.CourseService;
import cz.cvut.fel.bupro.test.configuration.AbstractControllerTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class CourseControllerTest extends AbstractControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Autowired
	private CourseService courseService;

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	private ProjectCourse getCourseInstnace() {
		return courseService.getProjectCourses(new Locale("cs")).iterator().next();
	}

	@Test
	@Transactional
	public void testGetCourseList() throws Exception {
		ProjectCourse course = getCourseInstnace();
		mockMvc.perform(get("/course/list"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("courseList"))
		.andExpect(view().name("course-list"))
		.andExpect(content().string(new Contains(course.getName())));
	}

	@Test
	@Transactional
	public void testGetCourse() throws Exception {
		ProjectCourse course = getCourseInstnace();
		mockMvc.perform(get("/course/edit/" + course.getId()))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("projectCourse"))
		.andExpect(view().name("course-edit"))
		.andExpect(content().string(new Contains(course.getCode())));
	}

	@Test
	@Transactional
	public void testCreateCourse() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/course/save");
		request.setContentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		request.setParameter("id", "");
		request.setParameter("code", "X36JPO");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Object handler = handlerMapping.getHandler(request).getHandler();
		ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertTrue(modelAndView.getViewName().contains("redirect:/course/list/"));
		ProjectCourse course = (ProjectCourse) modelAndView.getModelMap().get("projectCourse");
		Assert.assertNotNull(course.getId());
	}

}
