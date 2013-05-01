package cz.cvut.fel.bupro.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.cvut.fel.bupro.model.Registration;
import cz.cvut.fel.bupro.model.User;
import cz.cvut.fel.bupro.security.SecurityService;

@Controller
public class LoginController {

	@Autowired
	private SecurityService securityService;

	/** Login form. */
	@RequestMapping({ "/login" })
	public String login() {
		return "login";
	}

	/** Login form with error. */
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String singup(Model model) {
		model.addAttribute("registration", new Registration());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String singupComplete(@Valid Registration registration, BindingResult bindingResult, Model model, Locale locale) {
		if (bindingResult.hasErrors()) {
			return "signup";
		}
		User user = new User();
		user.setUsername(registration.getUsername());
		securityService.createUser(user, locale);
		return "redirect:/login";
	}

}
