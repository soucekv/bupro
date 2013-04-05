package cz.cvut.fel.bupro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.service.UserService;

@Controller
public class UserController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;

	@ModelAttribute("userList")
	public List<User> getUserList() {
		return userService.getAllUsers();
	}

	@RequestMapping({ "/user/list" })
	public String showUserList() {
		return "user-list";
	}

	@RequestMapping({ "/user/view/{id}" })
	public String showUserDetail(Model model, Locale locale, @PathVariable Long id) {
		log.trace("UserController.showUserDetail()");
		User user = userService.getUser(id);
		user.getComments().size(); // force fetch
		model.addAttribute("user", user);
		return "user-view";
	}

	@RequestMapping({ "/user/json" })
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
