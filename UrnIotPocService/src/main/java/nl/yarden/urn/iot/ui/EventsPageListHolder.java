/**
 * 
 */
package nl.yarden.urn.iot.ui;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;

import nl.yarden.urn.iot.beans.DevEUI_uplink;

/**
 * PageListHolder for IoT events.
 *
 */
public class EventsPageListHolder extends PagedListHolder<DevEUI_uplink> {
	private static final long serialVersionUID = -3496803471981485760L;

	/**
     * Create a new holder instance.
     */
    public EventsPageListHolder() {
        super();
    }

    /**
     * Create a new holder instance with the given source list.
     * @param source source the source List
     */
    public EventsPageListHolder(List<DevEUI_uplink> source) {
        super(source);
    }
}
