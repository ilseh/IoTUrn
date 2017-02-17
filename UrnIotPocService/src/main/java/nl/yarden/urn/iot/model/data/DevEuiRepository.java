/**
 *
 */
package nl.yarden.urn.iot.model.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.yarden.urn.iot.beans.DevEUI_uplink;

/**
 * Repository. Handler to db operations {@link DevEUI_uplink}.
 *
 */
public interface DevEuiRepository extends CrudRepository<DevEUI_uplink, Long> {

	@Query("select d from DevEUI_uplink d where d.DevEUI= ?1")
	List<DevEUI_uplink> findByDevEui(String devEUI);

}
