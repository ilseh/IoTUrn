/**
 *
 */
package nl.yarden.urn.iot.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Spring config.
 */
@Configuration
public class Config {
	@Autowired
	private AppConfig config;

	/**
	 * @return mailsender bean
	 */
	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(config.getSmtpConfig().getHost());
		sender.setUsername(config.getSmtpConfig().getUsername());
		Properties mailProps = new Properties();
		if (config.getSmtpConfig().isSecure()) {
			mailProps.setProperty("mail.smtp.auth", "true");
			mailProps.setProperty("mail.smtp.ssl.enable", "true");
			mailProps.setProperty("mail.transport.protocol", "smtps");
			sender.setPassword(config.getSmtpConfig().getPassword());
		}
		sender.setJavaMailProperties(mailProps);
		return sender;
	}
}
