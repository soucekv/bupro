package cz.cvut.fel.bupro.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.config.Qualifiers;
import cz.cvut.fel.bupro.dao.ProjectRepository;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.SemesterCode;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.kos.KosClient;
import cz.cvut.fel.kos.KosSemesterCode;
import cz.cvut.fel.kos.Period;
import cz.cvut.fel.kos.jaxb.Semester;

@Service
public class ExpirationService {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private KosClient kosClient;
	@Autowired
	private EmailService emailService;
	@Autowired
	@Qualifier(Qualifiers.EXPIRATION)
	private int limit;

	private static Map<User, Set<Project>> groupByUser(Collection<Project> projects) {
		Map<User, Set<Project>> map = new HashMap<User, Set<Project>>();
		for (Project project : projects) {
			User user = project.getOwner();
			Set<Project> set = map.get(user);
			if (set == null) {
				set = new HashSet<Project>();
				map.put(user, set);
			}
			set.add(project);
		}
		return map;
	}

	private DateTime parse(String date) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		return fmt.parseDateTime(date);
	}

	private void checkProjectsExpiration(DateTime now, Semester semester) {
		log.info("Check semester " + semester.getCode() + " " + semester.getEndDate());
		DateTime endDate = parse(semester.getEndDate());
		Days days = Days.daysBetween(now, endDate);
		if (days.getDays() < limit) {
			List<Project> projects = projectRepository.findByEndSemester(new SemesterCode(semester.getCode()));
			emailService.sendProjectExpiresWarning(groupByUser(projects), days.getDays());
		}
	}

	@Scheduled(cron = "0 0 3 * * * ")
	@Transactional
	public void checkProjectsExpiration() {
		log.info("Checking projects for expiration");
		DateTime now = new DateTime();
		Semester winterSemester = kosClient.getSemester(KosSemesterCode.encode(now.getYear(), Period.WINTER));
		checkProjectsExpiration(now, winterSemester);
		Semester summerSemester = kosClient.getSemester(KosSemesterCode.encode(now.getYear(), Period.SUMMER));
		checkProjectsExpiration(now, summerSemester);
	}
}
