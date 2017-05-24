package nl.yarden.urn.iot.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.yarden.urn.iot.beans.DevEUI_uplink;
import nl.yarden.urn.iot.beans.EventType;

/**
 * Change/enrich data.
 *
 */
@Component
public class DataTransformer {
	private static final Logger LOG = LoggerFactory.getLogger(DataTransformer.class);
	@Autowired
	private PayloadInterpreter payloadInterpreter;
	// Invalid datetime with 4 numbers in milliseconds field, ie 4311 in: 2017-05-11T09:49:24.4311+02:00
	private static final Pattern KPN_INVALID_DATETIME = Pattern.compile("(.*\\.)(\\d{4})(.*)");
	
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	
	/**
	 * Externalize event from payload to the DevEUI_uplink so it's easy to query on.
	 * @param uplink with payload and event that must be set
	 */
	public void setEvent(DevEUI_uplink uplink) {
		if (payloadInterpreter.isMovement(uplink.getPayload_hex())) {
			LOG.debug("Reveived movement event for {}", uplink.getDevEUI());
			uplink.setEventType(EventType.MOVE);
		}
	}
	
	public void correctTime(DevEUI_uplink uplink) {
		try {
			Matcher invalidDateTimeMatcher = KPN_INVALID_DATETIME.matcher(uplink.getTime());
			if (invalidDateTimeMatcher.matches()) {
				// Update milliseconds from xxxx to xxx.
				uplink.setTime(invalidDateTimeMatcher.group(1) + invalidDateTimeMatcher.group(2).substring(0, 3) 
								+ invalidDateTimeMatcher.group(3));
			} 
			uplink.setDateTime(SIMPLE_DATE_FORMAT.parse(uplink.getTime()));
		} catch (ParseException e) {
			LOG.error("Could not transform DevEUI time to datetime", e);
		}
	}
}
