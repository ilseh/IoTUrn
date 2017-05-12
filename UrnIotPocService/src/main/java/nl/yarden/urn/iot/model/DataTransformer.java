package nl.yarden.urn.iot.model;

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
	@Autowired
	private PayloadInterpreter payloadInterpreter;
	
	/**
	 * Externalize event from payload to the DevEUI_uplink so it's easy to query on.
	 * @param uplink with payload and event that must be set
	 */
	public void setEvent(DevEUI_uplink uplink) {
		if (payloadInterpreter.isMovement(uplink.getPayload_hex())) {
			uplink.setEventType(EventType.MOVE);
		}
	}
}
