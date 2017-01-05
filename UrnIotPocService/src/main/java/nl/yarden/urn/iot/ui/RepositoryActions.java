/**
 *
 */
package nl.yarden.urn.iot.ui;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.yarden.urn.iot.DevEuiRepository;
import nl.yarden.urn.iot.UrnRepository;
import nl.yarden.urn.iot.beans.DevEUI_uplink;
import nl.yarden.urn.iot.beans.Urn;

/**
 * Repository actions for UI stuff.
 *
 */
@Component
public class RepositoryActions {
	@Autowired
	private DevEuiRepository eventsRepository;
	@Autowired
	private UrnRepository urnRepository;

	/**
	 * @return all events
	 */
	public List<DevEUI_uplink> getAllEvents() {
		return StreamSupport.stream(eventsRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	/**
	 * @return all events for one urn.
	 */
	public List<DevEUI_uplink> getAllEvents(String urnTag) {
		return eventsRepository.findByDevEui(urnTag);
	}

	/**
	 * Save Urn config in db.
	 * @param urn with IoT device config
	 */
	public void saveUrn(Urn urn) {
		urnRepository.save(urn);
	}

	/**
	 * @return all urns
	 */
	public List<Urn> getAllUrns() {
		return StreamSupport.stream(urnRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	/**
	 * Get urn entries belonging by lastname.
	 * @param lastname
	 * @return
	 */
	public List<Urn> getDeceasedUrns(String lastname) {
		return StreamSupport.stream(urnRepository.findByLastName(lastname).spliterator(), false).collect(Collectors.toList());
	}

	/**
	 * Save urn event in db.
	 * @param event urn event
	 */
	public void saveUrnEvent(DevEUI_uplink event) {
		eventsRepository.save(event);
	}
}
