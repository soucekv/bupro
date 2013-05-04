package cz.cvut.fel.bupro.controller;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.cvut.fel.bupro.filter.Filterable;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.security.SecurityService;
import cz.cvut.fel.bupro.service.ProjectService;
import cz.cvut.fel.bupro.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SecurityService securityService;

	@RequestMapping("/list")
	@Transactional
	public String showUserList(Model model, Locale locale, @PageableDefaults Pageable pageable, Filterable filterable) {
		model.addAttribute("users", userService.getUsers(pageable, filterable));
		model.addAttribute("filter", filterable);
		return "user-list";
	}

	@RequestMapping("/view/{id}")
	@Transactional
	public String showUserDetail(Model model, Locale locale, @PathVariable Long id) {
		log.trace("UserController.showUserDetail()");
		User user = userService.getUser(id);
		user.getComments().size(); // force fetch
		boolean profile = securityService.getCurrentUser().equals(user);
		model.addAttribute("profile", profile);
		model.addAttribute("teached", projectService.getOwnedProjects(user, locale));
		model.addAttribute("membershiped", projectService.getMemberProjects(user, locale));
		model.addAttribute("user", user);
		return "user-view";
	}

	@RequestMapping("/save")
	@Transactional
	public String saveProject(User user, BindingResult bindingResult, Model model, Locale locale) {
		User u = securityService.getCurrentUser();
		if (!u.equals(user)) {
			log.warn("Invalid accesss not a logged user");
			return "redirect:/user/view/" + user.getId();
		}
		u.setAboutme(user.getAboutme());
		model.addAttribute("profile", true);
		model.addAttribute("teached", projectService.getOwnedProjects(user, locale));
		model.addAttribute("membershiped", projectService.getMemberProjects(user, locale));
		model.addAttribute("user", u);
		return "user-view";
	}

	@RequestMapping("/user/json")
	@Transactional
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String jsonUserList(Locale locale, @RequestParam String nameStartsWith, @RequestParam int maxRows) {
		Map<String, Object> jsonObject = new JSONObject();
		ArrayList<Object> items = new JSONArray();
		for (User user : userService.getByName(nameStartsWith, maxRows)) {
			Map<String, Object> userJson = new JSONObject();
			userJson.put("id", user.getId());
			userJson.put("firstName", user.getFirstName());
			userJson.put("lastName", user.getLastName());
			items.add(userJson);
		}
		jsonObject.put("items", items);
		return jsonObject.toString();
	}
}
