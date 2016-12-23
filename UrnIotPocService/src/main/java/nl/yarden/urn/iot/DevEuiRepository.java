/**
 *
 */
package nl.yarden.urn.iot;

import org.springframework.data.repository.CrudRepository;

import nl.yarden.urn.iot.beans.DevEUI_uplink;

/**
 * Repository. Handler to db operations {@link DevEUI_uplink}.
 *
 */
public interface DevEuiRepository extends CrudRepository<DevEUI_uplink, Long> {

}
