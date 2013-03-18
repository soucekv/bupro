package cz.cvut.fel.bupro.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.cvut.fel.bupro.model.Authorship;
import cz.cvut.fel.bupro.model.Comment;
import cz.cvut.fel.bupro.model.CommentableEntity;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.service.LoginService;
import cz.cvut.fel.bupro.service.ProjectService;
import cz.cvut.fel.bupro.service.UserService;

@Controller
public class CommentController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private LoginService loginService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;

	@SuppressWarnings("unchecked")
	private String asJSON(Locale locale, Comment comment) {
		JSONObject jsonObject = new JSONObject();
		if (comment.getAuthorship() != null) {
			User user = comment.getAuthorship().getAuthor();
			if (user != null) {
				String name = user.getFirstName() + " " + user.getLastName();
				jsonObject.put("author", name);
			}
			Timestamp timestamp = comment.getAuthorship().getCreationTime();
			if (timestamp != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", locale);
				jsonObject.put("creationtime", format.format(timestamp));
			}
		}
		jsonObject.put("title", comment.getTitle());
		jsonObject.put("text", comment.getText());
		return jsonObject.toJSONString();
	}

	private CommentableEntity find(String type, Long id) {
		if ("project".equalsIgnoreCase(type)) {
			return projectService.getProject(id);
		}
		if ("user".equalsIgnoreCase(type)) {
			return userService.getUser(id);
		}
		throw new IllegalArgumentException("Unknown type " + type);
	}

	@RequestMapping("/comment")
	@Transactional
	public @ResponseBody String save(Locale locale, @RequestParam String type, @RequestParam Long id, @RequestParam String title, @RequestParam String text) {
		log.info("comment on " + type + " by id:" + id + " title:'" + title + "' text:'" + text + "'");
		Comment comment = new Comment();
		comment.setAuthorship(new Authorship(loginService.getLoggedInUser()));
		comment.setTitle(title);
		comment.setText(text);
		CommentableEntity commentable = find(type, id);
		commentable.add(comment);
		return asJSON(locale, comment);
	}

}
