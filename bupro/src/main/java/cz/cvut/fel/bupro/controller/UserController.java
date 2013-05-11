package cz.cvut.fel.bupro.controller;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.cvut.fel.bupro.filter.Filterable;
import cz.cvut.fel.bupro.model.ChangePassword;
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

	private Locale[] getAvailableLocales() {
		return new Locale[]{ new Locale("cs"), Locale.ENGLISH};
	}

	private User getUser(String id) {
		return (String.valueOf(id).matches("^[0-9]+$")) ? userService.getUser(Long.parseLong(id)) : userService.getUserByUsername(id);
	}

	@RequestMapping("/list")
	@Transactional
	public String showUserList(Model model, Locale locale, @PageableDefaults Pageable pageable, Filterable filterable) {
		model.addAttribute("users", userService.getUsers(pageable, filterable));
		model.addAttribute("filter", filterable);
		return "user-list";
	}

	@RequestMapping("/view/{id}")
	@Transactional
	public String showUserDetail(Model model, Locale locale, @PathVariable String id) {
		log.trace("UserController.showUserDetail()");
		User user = getUser(id);
		user.getComments().size(); // force fetch
		boolean profile = securityService.getCurrentUser().equals(user);
		model.addAttribute("locales", getAvailableLocales());
		model.addAttribute("profile", profile);
		model.addAttribute("teached", projectService.getOwnedProjects(user, locale));
		model.addAttribute("membershiped", projectService.getMemberProjects(user, locale));
		model.addAttribute("user", user);
		return "user-view";
	}

	@RequestMapping("/edit/{id}")
	@Transactional
	public String edit(Model model, Locale locale, @PathVariable String id) {
		log.trace("UserController.showUserDetail()");
		User user = getUser(id);
		user.getComments().size(); // force fetch
		boolean profile = securityService.getCurrentUser().equals(user);
		model.addAttribute("profile", profile);
		model.addAttribute("user", user);
		model.addAttribute("changePassword", new ChangePassword());
		return "user-edit";
	}

	@RequestMapping("/save")
	@Transactional
	public String save(User user, BindingResult bindingResult, Model model, Locale locale) {
		User u = securityService.getCurrentUser();
		if (!u.equals(user)) {
			log.warn("Invalid accesss not a logged user");
			return "redirect:/user/view/" + user.getId();
		}
		u.setLang(user.getLang());
		u.setAboutme(user.getAboutme());
		model.addAttribute("locales", getAvailableLocales());
		model.addAttribute("profile", true);
		model.addAttribute("teached", projectService.getOwnedProjects(user, locale));
		model.addAttribute("membershiped", projectService.getMemberProjects(user, locale));
		model.addAttribute("user", u);
		return "user-view";
	}

	@RequestMapping("/json")
	@Transactional
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String jsonUserList(Locale locale, @RequestParam String nameStartsWith, @RequestParam int maxRows) {
		log.info("jsonUserList " + nameStartsWith);
		Map<String, Object> jsonObject = new JSONObject();
		ArrayList<Object> items = new JSONArray();
		for (User user : userService.getByName(nameStartsWith, maxRows)) {
			Map<String, Object> userJson = new JSONObject();
			userJson.put("id", user.getId());
			userJson.put("name", user.getFullName());
			items.add(userJson);
		}
		jsonObject.put("items", items);
		log.info("User list " + jsonObject.toString());
		return jsonObject.toString();
	}

	@RequestMapping("/change")
	@Transactional
	public String changePassword(@Valid ChangePassword changePassword, BindingResult bindingResult, Locale locale, Model model) {
		User user = securityService.getCurrentUser();
		boolean profile = user.getId().equals(changePassword.getId());
		if (bindingResult.hasErrors()) {
			log.error("Passwords change error " + bindingResult.getErrorCount() + " " + bindingResult.getAllErrors());
			model.addAttribute("profile", profile);
			return "user-edit";
		}
		if (!changePassword.isCorrect()) {
			log.error("Passwords does not match for user " + user.getId());
			bindingResult.rejectValue("reNewPassword", "error.password.match", "New passwords doesn't match");
			model.addAttribute("profile", profile);
			return "user-edit";
		}
		if (securityService.checkPassword(user, changePassword.getOldPassword())) {
			securityService.changePassword(user, changePassword.getNewPassword());
		} else {
			log.error("Invalid password for user " + user.getId());
			bindingResult.rejectValue("oldPassword", "error.password.old.match", "Incorrect password");
			model.addAttribute("profile", profile);
			return "user-edit";
		}
		return "redirect:/user/view/" + user.getId();
	}

	@RequestMapping("/resetpwd")
	@Transactional
	@Secured({"ROLE_ADMIN"})
	public String resetPassword(@RequestParam String user) {
		User u = getUser(user);
		log.info("Reset password for user " + u.getId());
		securityService.resetPassword(u, new Locale(u.getLang()));
		return "redirect:/user/view/" + u.getId();
	}
}
