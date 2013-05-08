package cz.cvut.fel.bupro.test.mock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cz.cvut.fel.bupro.service.EmailService;

@Primary
@Service
public class MockEmailService extends EmailService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void sendEmail(String to, String cc, String subject, String text) {
		log.info("Sending email to:" + to + " cc:" + cc + " title:'" + subject + "' body:'" + text + "'");
	}
}
