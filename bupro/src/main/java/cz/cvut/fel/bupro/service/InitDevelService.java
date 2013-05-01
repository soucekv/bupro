package cz.cvut.fel.bupro.service;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cz.cvut.fel.bupro.dao.CourseRepository;
import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.RoleRepository;
import cz.cvut.fel.bupro.dao.TagRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Comment;
import cz.cvut.fel.bupro.model.Membership;
import cz.cvut.fel.bupro.model.MembershipState;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.ProjectCourse;
import cz.cvut.fel.bupro.model.Role;
import cz.cvut.fel.bupro.model.SemesterCode;
import cz.cvut.fel.bupro.model.Tag;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.kos.KosClient;
import cz.cvut.fel.kos.jaxb.Course;
import cz.cvut.fel.reposapi.ServiceProvider;

@Service
public class InitDevelService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private KosClient kos;

	public void initDevelData() {
		log.info("Init Devel data");
		SemesterCode s1 = new SemesterCode(kos.getSemester().getCode());
		SemesterCode s2 = new SemesterCode(kos.getNextSemester().getCode());

		log.info("Kos semester code s1 " + s1);
		log.info("Kos semester code s2 " + s2);

		Role userRole = roleRepository.findByAuthority(Role.STUDENT);
		Role teacherRole = roleRepository.findByAuthority(Role.TEACHER);

		User user1 = new User();
		user1.setFirstName("Frantisek");
		user1.setLastName("Vomacka");
		user1.setEmail("vomacka@exmple.com");
		user1.setUsername("vomacka");
		user1.setPassword(passwordEncoder.encode("devel"));
		user1.getRoles().add(userRole);
		user1.getRoles().add(teacherRole);
		userRole.getUsers().add(user1);
		teacherRole.getUsers().add(user1);
		userRepository.save(user1);

		User user2 = new User();
		user2.setFirstName("Venca");
		user2.setLastName("Rohlik");
		user2.setEmail("rohlik@exmple.com");
		user2.setUsername("rohlik");
		user2.setPassword(passwordEncoder.encode("devel"));
		user2.getRoles().add(userRole);
		userRole.getUsers().add(user2);
		userRepository.save(user2);

		User user3 = new User();
		user3.setFirstName("Milan");
		user3.setLastName("Opicka");
		user3.setEmail("opicka@exmple.com");
		user3.setUsername("opicka");
		user3.setPassword(passwordEncoder.encode("devel"));
		userRepository.save(user3);

		User user4 = new User();
		user4.setFirstName("Pavel");
		user4.setLastName("Srb");
		user4.setEmail("srb@exmple.com");
		user4.setUsername("srb");
		user4.setPassword(passwordEncoder.encode("devel"));
		userRepository.save(user4);

		Course course = kos.getCourse("X36PMI");

		ProjectCourse c1 = new ProjectCourse();
		c1.setCode(course.getCode());
		courseRepository.save(c1);

		course = kos.getCourse("X36PAR");
		ProjectCourse c2 = new ProjectCourse();
		c2.setCode(course.getCode());
		courseRepository.save(c2);

		Project p1 = new Project();
		p1.setName("Test 1");
		p1.setOwner(user1);
		p1.setCapacity(2);
		p1.setCourse(c1);
		p1.setStartSemester(s1);
		p1.setEndSemester(s2);
		p1.getRepository().setRepositoryUser("soucekv");
		p1.getRepository().setRepositoryName("bupro");
		p1.getRepository().setRepositoryProvider(ServiceProvider.GITHUB);
		Comment comment = new Comment();
		comment.setUser(user1);
		comment.setTitle("Some extra notes");
		comment.setText("Dont forget to get extra reward for this project");
		p1.add(comment);

		Membership membership = new Membership();
		membership.setUser(user3);
		membership.setProject(p1);
		p1.getMemberships().add(membership);

		membership = new Membership();
		membership.setUser(user2);
		membership.setProject(p1);
		p1.getMemberships().add(membership);

		membership = new Membership();
		membership.setUser(user4);
		membership.setProject(p1);
		p1.getMemberships().add(membership);

		projectRepository.save(p1);

		Project p2 = new Project();
		p2.setName("Test 2");
		p2.setOwner(user2);
		p2.setCourse(c2);
		p2.setStartSemester(s1);
		p2.setEndSemester(s2);
		p2.getRepository().setRepositoryUser("mneorr");
		p2.getRepository().setRepositoryName("Alcatraz");
		p2.getRepository().setRepositoryProvider(ServiceProvider.GITHUB);

		membership = new Membership();
		membership.setMembershipState(MembershipState.APPROVED);
		membership.setUser(user1);
		membership.setProject(p2);
		p2.getMemberships().add(membership);

		projectRepository.save(p2);
		Tag t1 = new Tag("tag1");
		tagRepository.save(Arrays.asList(new Tag("tag2"), new Tag("special"), new Tag("one two")));

		p1.getTags().add(t1);
		projectRepository.save(p1);

		for (int i = 0; i < 25; i++) {
			Project project = new Project();
			project.setName("Pagination " + i);
			project.setOwner(user2);
			project.setCourse(c1);
			project.setStartSemester(s1);
			project.setEndSemester(s2);
			projectRepository.save(project);
		}
	}

}
