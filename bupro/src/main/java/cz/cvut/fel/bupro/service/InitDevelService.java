package cz.cvut.fel.bupro.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.dao.SemesterRepository;
import cz.cvut.fel.bupro.dao.SubjectRepository;
import cz.cvut.fel.bupro.dao.TagRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.model.Comment;
import cz.cvut.fel.bupro.model.Enrolment;
import cz.cvut.fel.bupro.model.EnrolmentType;
import cz.cvut.fel.bupro.model.Membership;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.Semester;
import cz.cvut.fel.bupro.model.Subject;
import cz.cvut.fel.bupro.model.Tag;
import cz.cvut.fel.bupro.model.User;

@Service
public class InitDevelService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private SemesterRepository semesterRepository;

	@PostConstruct
	@Transactional
	public void initDevelData() {
		log.info("Init Devel data");
		Semester s1 = new Semester();
		s1.setCode("201314Z");
		s1.setName("Winter Semester 2013/2014");
		semesterRepository.save(s1);

		Semester s2 = new Semester();
		s2.setCode("201314L");
		s2.setName("Summer Semester 2013/2014");
		semesterRepository.save(s2);

		User user1 = new User();
		user1.setFirstName("Frantisek");
		user1.setLastName("Vomacka");
		user1.setEmail("vomacka@exmple.com");
		user1.setUsername("vomacka");
		userRepository.save(user1);

		User user2 = new User();
		user2.setFirstName("Venca");
		user2.setLastName("Rohlik");
		user2.setEmail("rohlik@exmple.com");
		user2.setUsername("rohlik");
		userRepository.save(user2);

		User user3 = new User();
		user3.setFirstName("Milan");
		user3.setLastName("Opicka");
		user3.setEmail("opicka@exmple.com");
		user3.setUsername("opicka");
		userRepository.save(user3);

		Subject subject = new Subject();
		subject.setName("X7 - Happy Subject");
		Enrolment enrolment = new Enrolment();
		enrolment.setEnrolmentType(EnrolmentType.TEACHER);
		enrolment.setUser(user1);
		enrolment.setSubject(subject);
		subject.getEnrolments().add(enrolment);
		subjectRepository.save(subject);

		Subject subject2 = new Subject();
		subject2.setName("X13 - Sad Subject");
		subjectRepository.save(subject2);

		Project p1 = new Project();
		p1.setName("Test 1");
		p1.setOwner(user1);
		p1.setSubject(subject);
		p1.setStartSemester(s1);
		p1.setEndSemester(s2);
		Comment comment = new Comment();
		comment.setUser(user1);
		comment.setTitle("Some extra notes");
		comment.setText("Dont forget to get extra reward for this project");
		p1.add(comment);
		Membership membership = new Membership();
		membership.setUser(user3);
		membership.setProject(p1);
		p1.getMemberships().add(membership);

		projectRepository.save(p1);

		Project p2 = new Project();
		p2.setName("Test 2");
		p2.setOwner(user2);
		p2.setSubject(subject);
		p2.setStartSemester(s1);
		p2.setEndSemester(s2);
		membership = new Membership();
		membership.setUser(user3);
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
			project.setOwner(user1);
			project.setSubject(subject);
			project.setStartSemester(s1);
			project.setEndSemester(s2);
			projectRepository.save(project);
		}
	}

}
