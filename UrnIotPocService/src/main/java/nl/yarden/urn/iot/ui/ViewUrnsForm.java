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
public class ViewUrnsForm extends Form {
	private List<Urn> urn;

	/**
	 * @return the urn
	 */
	public List<Urn> getUrn() {
		return urn;
	}

	/**
	 * @param urn the urn to set
	 */
	public void setUrn(List<Urn> urn) {
		this.urn = urn;
	}

}
