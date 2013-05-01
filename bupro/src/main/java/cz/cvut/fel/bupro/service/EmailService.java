package cz.cvut.fel.bupro.service;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fel.bupro.config.Qualifiers;
import cz.cvut.fel.bupro.model.Email;
import cz.cvut.fel.bupro.model.MembershipState;
import cz.cvut.fel.bupro.model.Project;
import cz.cvut.fel.bupro.model.User;

/**
 * Service for sending auto-generated messages (notifications) from system
 */
@Service
public class EmailService {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	@Qualifier(Qualifiers.EMAIL)
	private MessageSource emailsMessageSource;
	@Autowired
	private MessageSource messageSource;

	private static String projectLink(String urlBase, Project project) {
		if (!urlBase.endsWith("/")) {
			urlBase += "/";
		}
		return urlBase + "project/view/" + project.getId();
	}

	private static String userLink(String urlBase, User user) {
		if (!urlBase.endsWith("/")) {
			urlBase += "/";
		}
		return urlBase + "user/view/" + user.getId();
	}

	private String getLocalizedFullName(Locale locale, String firstName, String lastName) {
		return messageSource.getMessage("format.fullname", new String[] { firstName, lastName }, firstName + " " + lastName, locale);
	}

	private String getLocalizedFullName(Locale locale, User user) {
		return getLocalizedFullName(locale, user.getFirstName(), user.getLastName());
	}

	public void sendMembershipAutoapproved(String linkUrl, Locale locale, Project project, User user) {
		String title = emailsMessageSource.getMessage("notify.new.membership.autoapproved.title", new String[] {}, "Bupro: membership automaticaliy approved",
				locale);
		String defaultBody = "User " + getLocalizedFullName(locale, user) + " joined your project " + project.getName();
		String[] args = new String[] { project.getName(), projectLink(linkUrl, project), getLocalizedFullName(locale, user), userLink(linkUrl, user) };
		String body = emailsMessageSource.getMessage("notify.new.membership.autoapproved.text", args, defaultBody, locale);
		sendEmail(project.getOwner(), title, body);
	}

	public void sendMembershipRequest(String linkUrl, Locale locale, Project project, User user) {
		String title = emailsMessageSource.getMessage("notify.new.membership.request.title", new String[] {}, "Bupro: membership request", locale);
		String defaultBody = "User " + getLocalizedFullName(locale, user) + " requested to join your project " + project.getName();
		String[] args = new String[] { project.getName(), projectLink(linkUrl, project), getLocalizedFullName(locale, user), userLink(linkUrl, user) };
		String body = emailsMessageSource.getMessage("notify.new.membership.request.text", args, defaultBody, locale);
		sendEmail(project.getOwner(), title, body);
	}

	public void sendMembershipState(String linkUrl, Locale locale, Project project, User user, MembershipState membershipState) {
		final String titleKey = "notify.membership.request." + String.valueOf(membershipState).toLowerCase() + ".title";
		final String textKey = "notify.membership.request." + String.valueOf(membershipState).toLowerCase() + ".text";
		String title = emailsMessageSource.getMessage(titleKey, new String[] {}, "Bupro: membership " + String.valueOf(membershipState), locale);
		String[] args = new String[] { project.getName(), projectLink(linkUrl, project) };
		String defaultText = "Your request to join project " + project.getName() + " " + String.valueOf(membershipState).toLowerCase();
		String text = emailsMessageSource.getMessage(textKey, args, defaultText, locale);
		sendEmail(user, title, text);
	}

	@Transactional
	public void sendEmail(User to, String title, String body) {
		sendEmail(to.getEmail(), null, title, body);
	}

	public void sendEmail(String to, String cc, String title, String body) {
		log.info("Sending email to:" + to + " cc:" + cc + " title:'" + title + "' body:'" + body + "'");
		// TODO implement javax mail sending
	}

	public void sendEmail(Email email) {
		sendEmail(email.getTo(), email.getC(), email.getTitle(), email.getText());
	}

	public void sendRegistrationEmail(User user, String password, Locale locale) {
		final String titleKey = "notify.user.registration.title";
		final String textKey = "notify.user.registration.text";
		String title = emailsMessageSource.getMessage(titleKey, new String[] {}, "Bupro: registration complete", locale);
		String[] args = new String[] { user.getUsername(), password };
		String defaultText = "Welcome to bupro. Your registration user:" + user.getUsername() + " pass:" + password;
		String text = emailsMessageSource.getMessage(textKey, args, defaultText, locale);
		sendEmail(user, title, text);
	}
}
