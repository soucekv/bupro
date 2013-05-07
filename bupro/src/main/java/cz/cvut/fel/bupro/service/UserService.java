package cz.cvut.fel.bupro.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.dao.RoleRepository;
import cz.cvut.fel.bupro.dao.UserRepository;
import cz.cvut.fel.bupro.filter.FilterSpecificationFactory;
import cz.cvut.fel.bupro.filter.Filterable;
import cz.cvut.fel.bupro.model.Role;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.support.RandomPasswordGenerator;
import cz.cvut.fel.kos.KosClient;
import cz.cvut.fel.kos.jaxb.Student;
import cz.cvut.fel.kos.jaxb.Teacher;

@Service
public class UserService {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RandomPasswordGenerator passwordGenerator;
	@Autowired
	private KosClient kos;

	@Transactional
	public User getUser(Long id) {
		log.debug("get user id:" + id);
		User user = userRepository.findOne(id);
		user.getComments().size(); // force fetch
		user.getRoles().size(); // force fetch
		return user;
	}

	public User getUserByUsername(String username) {
		log.debug("get user username:" + username);
		return userRepository.findByUsername(username);
	}

	public User createNewUserAccount(String username, Locale locale) {
		log.info("New account requested");
		User user = getUserByUsername(username);
		if (user != null) {
			log.warn("Can't create account user " + username + " already exsits");
			return null;
		}
		user = new User();
		user.setUsername(username);
		String email = null;
		Student student = kos.getStudent(username);
		if (student != null) {
			email = student.getEmail();
			user.setTitlePre(student.getTitlesPre());
			user.setFirstName(student.getFirstName());
			user.setLastName(student.getLastName());
			user.setTitlePost(student.getTitlesPost());
			Role studentRole = roleRepository.findByAuthority(Role.STUDENT);
			studentRole.grantTo(user);
		}
		Teacher teacher = kos.getTeacher(username);
		if (teacher != null) {
			email = teacher.getEmail();
			user.setTitlePre(teacher.getTitlesPre());
			user.setFirstName(teacher.getFirstName());
			user.setLastName(teacher.getLastName());
			user.setTitlePost(teacher.getTitlesPost());
			Role teacherRole = roleRepository.findByAuthority(Role.TEACHER);
			teacherRole.grantTo(user);
		}
		if (email == null) {
			log.warn("User " + username + " not found in KOS!");
			return null;
		}
		user.setEmail(email);
		String password = passwordGenerator.generate(8);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		emailService.sendRegistrationEmail(user, password, locale);
		return user;
	}

	@Transactional
	public List<User> getAllUsers() {
		log.info("get all users");
		return userRepository.findAll();
	}

	public Page<User> getUsers(Pageable pageable, Filterable filterable) {
		Specification<User> spec = createSpecification(filterable);
		if (spec == null) {
			return userRepository.findAll(pageable);
		}
		return userRepository.findAll(spec, pageable);
	}

	@Transactional
	public List<User> getByName(String name, int maxResults) {
		String hint = name + "%";
		return userRepository.findByFirstNameLikeOrLastNameLike(hint, hint, new PageRequest(0, maxResults));
	}

	private static Specifications<User> createSingleSpec(Specifications<User> spec, Map<String, String> map, FilterKey key) {
		Specification<User> newSpec = createSingleSpec(map, key);
		if (newSpec == null) {
			return spec;
		}
		if (spec == null) {
			return Specifications.where(newSpec);
		}
		return spec.and(newSpec);
	}

	private static Specification<User> createSingleSpec(Map<String, String> map, FilterKey key) {
		final String value = map.get(key.toString());
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		return key.create(value);
	}

	private static Specification<User> createSpecification(Filterable filterable) {
		return createSpecification(filterable.getFilter());
	}

	private static Specification<User> createSpecification(Map<String, String> map) {
		Specifications<User> specifications = null;
		for (FilterKey key : FilterKey.values()) {
			specifications = createSingleSpec(specifications, map, key);
		}
		return specifications;
	}

	private static enum FilterKey implements FilterSpecificationFactory<User> {
		FIRST_NAME {
			public Specification<User> create(final String value) {
				return new Specification<User>() {
					public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						return cb.like(root.<String> get("firstName"), "%" + value + "%");
					}
				};
			}
		};
	}

}
