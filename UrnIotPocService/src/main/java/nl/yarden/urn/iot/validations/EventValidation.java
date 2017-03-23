/**
 *
 */
package nl.yarden.urn.iot.validations;

import org.springframework.stereotype.Component;

import nl.yarden.urn.iot.beans.Urn;

/**
 * @author ilse.haanstra
 *
 */
@Component
public class EventValidation {

	/**
	 * Validation to check if event emitted by device was initialized/announced in this application.
	 * @param urn that emmitted event
	 * @param event that was emitted
	 */
	public boolean isValidMovement(Urn urn, String event) {
		return event.equals(urn.getCurrentStatus());
	}

}
