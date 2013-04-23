package cz.cvut.fel.bupro.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fel.bupro.model.SemesterCode;
import cz.cvut.fel.kos.KosClient;
import cz.cvut.fel.kos.Translator;
import cz.cvut.fel.kos.jaxb.Semester;

/**
 * Provides Semester data for application
 * 
 * @author Viktor Souček
 * 
 */
@Service
public class SemesterService {

	@Autowired
	private KosClient kosClient;

	public List<Semester> getAllSemesters() {
		return kosClient.getSemesters();
	}

	public List<Semester> getFutureSemesters() {
		// TODO implement this method
		throw new UnsupportedOperationException();
	}

	public Semester getSemester(SemesterCode code) {
		return kosClient.getSemester(code.getCode());
	}

	public Map<SemesterCode, Semester> getSemesters(Collection<SemesterCode> c) {
		Map<SemesterCode, Semester> map = new HashMap<SemesterCode, Semester>();
		for (SemesterCode code : c) {
			if (!map.containsKey(code)) {
				map.put(code, getSemester(code));
			}
		}
		return map;
	}

	public Map<SemesterCode, String> getSemestersNames(Collection<SemesterCode> c, Locale locale) {
		Translator translator = new Translator(locale);
		Map<SemesterCode, String> map = new HashMap<SemesterCode, String>();
		for (SemesterCode code : c) {
			if (!map.containsKey(code)) {
				Semester semester = getSemester(code);
				map.put(code, translator.localizedString(semester.getName()));
			}
		}
		return map;
	}

}
