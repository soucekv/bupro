package cz.cvut.fel.bupro.test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Contains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import cz.cvut.fel.bupro.TimeUtils;
import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Membership;
import cz.cvut.fel.bupro.model.MembershipState;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.ProjectCourse;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.security.SecurityService;
import cz.cvut.fel.bupro.test.configuration.AbstractControllerTest;
import cz.cvut.fel.kos.KosSemesterCode;
import cz.cvut.fel.kos.Period;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectControllerTest extends AbstractControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;
	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	private Project getProjectInstnace() {
		return projectRepository.findAll(new Pageable() {

			public Sort getSort() {
				return new Sort("name");
			}

			public int getPageSize() {
				return 10;
			}

			public int getPageNumber() {
				return 0;
			}

			public int getOffset() {
				return 0;
			}
		}).iterator().next();
	}

	private ProjectCourse getCourseInstance() {
		return getProjectInstnace().getCourse();
	}

	private User getUserInstance() {
		return userRepository.findByUsername("vomacka");
	}

	private Project createProject() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/project/save");
		request.setContentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		request.setParameter("id", "");
		request.setParameter("name", "Test Project");
		request.setParameter("course", String.valueOf(getCourseInstance().getId()));
		request.setParameter("startSemester", KosSemesterCode.encode(2013, Period.WINTER));
		request.setParameter("endSemester", KosSemesterCode.encode(2013, Period.WINTER));
		request.setParameter("capacity", String.valueOf(1));
		request.setParameter("description", "test description");
		request.setParameter("autoApprove", "true");
		request.setParameter("tagGroup", "Software");
		request.setParameter("tags", "Java, JSF, JPA");
		request.setParameter("repository.repositoryProvider", "GITHUB");
		request.setParameter("repository.repositoryUser", "soucekv");
		request.setParameter("repository.repositoryName", "bupro");
		request.setParameter("repository.applicationId", "");
		request.setParameter("repository.applicationSecret", "");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Object handler = handlerMapping.getHandler(request).getHandler();
		ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertTrue(modelAndView.getViewName().contains("redirect:/project/view/"));
		Project project = (Project) modelAndView.getModelMap().get("project");
		Assert.assertNotNull(project.getId());
		Membership membership = new Membership();
		membership.setCreated(TimeUtils.createCurrentTimestamp());
		membership.setChanged(membership.getCreated());
		membership.setProject(project);
		membership.setUser(getUserInstance());
		project.getMemberships().add(membership);
		return project;
	}

	@Test
	@Transactional
	public void testGetProjectList() throws Exception {
		Project project = getProjectInstnace();
		mockMvc.perform(get("/project/list"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("projects"))
			.andExpect(view().name("project-list"))
			.andExpect(content().string(new Contains(project.getName())))
			.andExpect(content().string(new Contains("/project/view/" + project.getId())));
	}

	@Test
	@Transactional
	public void testGetProject() throws Exception {
		Project project = getProjectInstnace();
		mockMvc.perform(get("/project/view/" + project.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("project"))
			.andExpect(model().attribute("project", project))
			.andExpect(view().name("project-view"))
			.andExpect(content().string(new Contains(project.getName())))
			.andExpect(content().string(new Contains(project.getOwner().getFullName())));
	}

	@Test
	@Transactional
	public void testGetEditProject() throws Exception {
		Project project = getProjectInstnace();
		mockMvc.perform(get("/project/edit/" + project.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("project"))
			.andExpect(model().attribute("project", project))
			.andExpect(view().name("project-edit"))
			.andExpect(content().string(new Contains(project.getName())));
	}

	@Test
	@Transactional
	public void testSaveProject() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/project/save");
		request.setContentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		request.setParameter("id", "");
		request.setParameter("name", "Test Project");
		request.setParameter("course", String.valueOf(getCourseInstance().getId()));
		request.setParameter("startSemester", KosSemesterCode.encode(2013, Period.WINTER));
		request.setParameter("endSemester", KosSemesterCode.encode(2013, Period.WINTER));
		request.setParameter("capacity", String.valueOf(1));
		request.setParameter("description", "test description");
		request.setParameter("autoApprove", "true");
		request.setParameter("tagGroup", "Software");
		request.setParameter("tags", "Java, JSF, JPA");
		request.setParameter("repository.repositoryProvider", "GITHUB");
		request.setParameter("repository.repositoryUser", "soucekv");
		request.setParameter("repository.repositoryName", "bupro");
		request.setParameter("repository.applicationId", "");
		request.setParameter("repository.applicationSecret", "");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Object handler = handlerMapping.getHandler(request).getHandler();
		ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertTrue(modelAndView.getViewName().contains("redirect:/project/view/"));
		Project project = (Project) modelAndView.getModelMap().get("project");
		Assert.assertNotNull(project.getId());
	}

	@Test
	@Transactional
	public void testApproveProjectMember() throws Exception {
		Project project = createProject();
		Membership membership = project.getMemberships(MembershipState.WAITING_APPROVAL).iterator().next();
		User user = membership.getUser();
		mockMvc.perform(get("/project/membership/approve?projectId=" + project.getId() + "&userId=" + user.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("project"))
			.andExpect(model().attribute("project", project))
			.andExpect(view().name("project-view"))
			.andExpect(content().string(new Contains(project.getName())))
			.andExpect(content().string(new Contains("approved")));
	}

	@Test
	@Transactional
	public void testDeclineProjectMember() throws Exception {
		Project project = createProject();
		Membership membership = project.getMemberships(MembershipState.WAITING_APPROVAL).iterator().next();
		User user = membership.getUser();
		mockMvc.perform(get("/project/membership/decline?projectId=" + project.getId() + "&userId=" + user.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("project"))
			.andExpect(model().attribute("project", project))
			.andExpect(view().name("project-view"))
			.andExpect(content().string(new Contains(project.getName())))
			.andExpect(content().string(new Contains("declined")));
	}

	private Project getNotOwnedProjectInstnace() {
		List<Project> projects = projectRepository.findAll();
		for (Project project : projects) {
			if (!project.getOwner().equals(securityService.getCurrentUser())) {
				return project;
			}
		}
		throw new IllegalStateException("Proect of other user not found!");
	}

	@Test
	@Transactional
	public void testJoinProject() throws Exception {
		Project project = getNotOwnedProjectInstnace();
		mockMvc.perform(get("/project/join/" + project.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("project"))
			.andExpect(model().attribute("project", project))
			.andExpect(view().name("project-view"))
			.andExpect(content().string(new Contains(project.getName())))
			.andExpect(content().string(new Contains(securityService.getCurrentUser().getFullName())));
	}

}
