/**
 *
 */
package nl.yarden.urn.iot.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import nl.yarden.urn.iot.beans.DevEUI_uplink;
import nl.yarden.urn.iot.beans.Urn;

/**
 * Iot model.
 *
 */
@Component
public class IotModel {
	private static final Logger LOG = LoggerFactory.getLogger(IotModel.class);
	@Autowired
	private RepositoryActions repositories;
	@Autowired
	private DeviceActions deviceCommands;
	private static final String HTTPEXCEPTION_404 = "404 Not Found";
	private static final String STATUS_NOT_FOUND = "Device bestaat niet";
	private static final int MAX_LENGTH_ERR_STATUS = 50;

	/**
	 * Save new urn config.
	 * @param urn config to save
	 */
	public void saveUrn(Urn urn) {
		repositories.saveUrn(urn);
	}

	/**
	 * @param lastname of deceased.
	 * @return urns of deceased persons with same lastname
	 */
	public List<Urn> getDeceasedUrns(String lastname) {
		return repositories.getDeceasedUrns(lastname);
	}

	/**
	 * @return all urns
	 */
	public List<Urn> getAllUrns() {
		return repositories.getAllUrns();
	}

	/**
	 * Save urn event in db.
	 * @param event urn event
	 */
	public void saveUrnEvent(DevEUI_uplink event) {
		repositories.saveUrnEvent(event);
	}

	/**
	 * @return all events for one urn.
	 */
	public List<DevEUI_uplink> getAllEvents(String urnTag) {
		return repositories.getAllEvents(urnTag);
	}

	/**
	 * Turn light on or off and stores current status in db.
	 * @param on true turns light on, false turns light off
	 * @param deviceId id of device
	 * @return current status
	 */
	public String turnLight(boolean on, String deviceId) {
		String action = on ? "on" : "off";
		LOG.debug("Turn light {} for device: {}", action, deviceId);
		String status = on ? UrnStatus.SEARCH.getValue() : UrnStatus.NONE.getValue();
		try {
			deviceCommands.turnLight(on, deviceId);
		} catch (HttpClientErrorException ex) {
			if (HTTPEXCEPTION_404.contains(ex.getMessage())) {
				status = STATUS_NOT_FOUND;
			} else {
				status = ex.getStatusText().substring(0, MAX_LENGTH_ERR_STATUS);
			}
		} catch (Exception ex) {
			status = ex.getMessage();
		}
		Urn urn = repositories.getUrnByDeviceId(deviceId);
		urn.setCurrentStatus(status);
		saveUrn(urn);
		return status;
	}

	/**
	 * Delete urn.
	 * @param deviceId of urn
	 */
	public void deleteUrn(String deviceId) {
		Urn urn = repositories.getUrnByDeviceId(deviceId);
		repositories.deleteUrn(urn);
	}
}
