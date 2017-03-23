/**
 *
 */
package nl.yarden.urn.iot.utils.mail;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Email utility.
 */
@Component
public class EmailUtil {
	private static final Logger LOG = LoggerFactory.getLogger(EmailUtil.class);
	private static final int PRIO_HIGH = 1;
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Send email.
	 * @param recipients of the email
	 * @param sender of the email
	 * @param subject of the email
	 * @param text in the email
	 * @param prio mail priority
	 * @throws MessagingException if something goes wrong
	 */
	public void sendEmail(List<String> recipients, String sender, String subject, String text, Boolean prio) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessage = getMimeMessageHelper(message);
		mimeMessage.setTo(recipients.toArray(new String[recipients.size()]));
		mimeMessage.setFrom(sender);
		mimeMessage.setSubject(subject);
		mimeMessage.setText(text);
		if (prio != null && prio) {
			mimeMessage.setPriority(PRIO_HIGH);
		}
		mailSender.send(message);
		LOG.debug("Email sent");
	}

	// Getter to facilitate unittest.
	MimeMessageHelper getMimeMessageHelper(MimeMessage message) {
		return new MimeMessageHelper(message);
	}
}
