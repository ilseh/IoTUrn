/**
 *
 */
package nl.yarden.urn.iot.model;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.yarden.urn.iot.beans.DevEUI_uplink;
import nl.yarden.urn.iot.beans.Urn;
import nl.yarden.urn.iot.config.AppConfig;
import nl.yarden.urn.iot.utils.mail.EmailUtil;
import nl.yarden.urn.iot.validations.EventValidation;

/**
 * Handler of urn events.
 *
 */
@Component
public class EventHandler {
	private static final Logger LOG = LoggerFactory.getLogger(EventHandler.class);
	@Autowired
	private EventValidation eventValidation;
	@Autowired
	private RepositoryActions repositoryActions;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private AppConfig config;

	private static final String EMAIL_SUBJECT = "Urn %s is bewogen zonder dat dit is aangemeld";
	private static final String EMAIL_TEXT = "Urn %s is bewogen zonder dat dit is aangemeld\r\n\r\n"
			+ "[Dit is een automatisch gegenereerde email.]";

	/**
	 * Handle move event of urn.
	 * @param uplink message of the urn.
	 */
	public void handleMoveEvent(DevEUI_uplink uplink) {
		Urn urn = repositoryActions.getUrnByDeviceId(uplink.getDevEUI());
		if (!eventValidation.isValidMovement(urn, UrnStatus.SEARCH.getValue())) {
			urn.setCurrentStatus(UrnStatus.INVALID_MOVE);
			sendEmail(urn);
		}
		repositoryActions.saveUrn(urn);
	}

	private void sendEmail(Urn urn) {
		try {
			emailUtil.sendEmail(Arrays.asList(config.getMailTo()), config.getMailFrom(), getMailSubject(urn), getMailText(urn), true);
		} catch (Throwable e) {
			LOG.error("Could not send email", e);
		}
	}

	private String getMailSubject(Urn urn) {
		return String.format(EMAIL_SUBJECT, urn);
	}

	private String getMailText(Urn urn) {
		return String.format(EMAIL_TEXT, urn);
	}
}
