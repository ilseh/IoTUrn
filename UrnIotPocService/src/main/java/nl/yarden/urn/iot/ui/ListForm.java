/**
 *
 */
package nl.yarden.urn.iot.ui;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;

/**
 * Form for handling IoT events.
 *
 */
public class ListForm<T> extends Form {
	private PagedListHolder<T> eventsPagedList;
	private List<String> columnHeaders;


	/**
	 * Default constructor.
	 */
	public ListForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param eventsPagedList to use
	 */
	public ListForm(PagedListHolder<T> eventsPagedList) {
		super();
		this.eventsPagedList = eventsPagedList;
	}

	/**
	 * @return the columnHeaders
	 */
	public List<String> getColumnHeaders() {
		return columnHeaders;
	}

	/**
	 * @return the eventsPagedList
	 */
	public PagedListHolder<T> getEventsPagedList() {
		return eventsPagedList;
	}

	/**
	 * @param columnHeaders the columnHeaders to set
	 */
	public void setColumnHeaders(List<String> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	/**
	 * @param eventsPagedList the eventsPagedList to set
	 */
	public void setEventsPagedList(PagedListHolder<T> eventsPagedList) {
		this.eventsPagedList = eventsPagedList;
	}

}
