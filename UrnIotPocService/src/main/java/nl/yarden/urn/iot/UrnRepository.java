/**
 *
 */
package nl.yarden.urn.iot;

import org.springframework.data.repository.CrudRepository;

import nl.yarden.urn.iot.beans.Urn;

/**
 * Repository. Handler to db operations {@link Urn}.
 *
 */
public interface UrnRepository extends CrudRepository<Urn, Long> {

}
