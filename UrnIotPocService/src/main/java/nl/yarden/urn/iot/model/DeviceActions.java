/**
 *
 */
package nl.yarden.urn.iot.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.yarden.urn.iot.model.iot.Processor;

/**
 * Helper to execute device actions.
 *
 */
@Component
public class DeviceActions {
	@Autowired
	private Processor devicesProcessor;

	/**
	 * @param on light on, else of
	 * @param deviceId of device whose light must be on or off
	 */
	public void turnLight(boolean on, String deviceId) {
		if (on) {
			devicesProcessor.turnLightOn(deviceId);
		} else {
			devicesProcessor.turnLightOff(deviceId);
		}
	}
}
