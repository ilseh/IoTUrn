/**
 * 
 */
package nl.yarden.urn.iot;

import org.springframework.data.repository.CrudRepository;

import nl.yarden.urn.iot.beans.DevEUI_uplink;

/**
 * Repository.
 *
 */
public interface IotRepository extends CrudRepository<DevEUI_uplink, Long> {

}
