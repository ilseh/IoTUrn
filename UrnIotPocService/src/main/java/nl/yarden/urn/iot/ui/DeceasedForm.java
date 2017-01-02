/**
 *
 */
package nl.yarden.urn.iot.ui;

import java.util.List;

import nl.yarden.urn.iot.beans.Urn;

/**
 * Form for viewing urns.
 *
 */
public class DeceasedForm extends Form {
	private String searchLastName;

	private List<Urn> urns;

	/**
	 * @return the searchLastName
	 */
	public String getSearchLastName() {
		return searchLastName;
	}

	/**
	 * @param searchLastName the searchLastName to set
	 */
	public void setSearchLastName(String searchLastName) {
		this.searchLastName = searchLastName;
	}

	/**
	 * @return the urns
	 */
	public List<Urn> getUrns() {
		return urns;
	}

	/**
	 * @param urns the urns to set
	 */
	public void setUrns(List<Urn> urns) {
		this.urns = urns;
	}

}
