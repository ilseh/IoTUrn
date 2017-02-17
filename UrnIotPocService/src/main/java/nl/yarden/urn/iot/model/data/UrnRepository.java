/**
 *
 */
package nl.yarden.urn.iot.model.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.yarden.urn.iot.beans.Urn;

/**
 * Repository. Handler to db operations {@link Urn}.
 *
 */
public interface UrnRepository extends CrudRepository<Urn, Long> {
	@Query("select u from Urn u where u.deceasedLastName = ?1")
	List<Urn> findByLastName(String lastname);

	@Query("select u from Urn u where u.DevEUI = ?1")
	Urn findByDeviceId(String deviceId);
}
