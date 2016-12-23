/**
 *
 */
package nl.yarden.urn.iot.ui;

import nl.yarden.urn.iot.beans.Urn;

/**
 * Form for handling administration urn.
 *
 */
public class UrnForm extends Form {
	private Urn urn;

	/**
	 * @return the urn
	 */
	public Urn getUrn() {
		return urn;
	}

	/**
	 * @param urn the urn to set
	 */
	public void setUrn(Urn urn) {
		this.urn = urn;
	}


}
