/**
 *
 */
package nl.yarden.urn.iot.model;

/**
 * @author ilse.haanstra
 *
 */
public enum UrnStatus {
	SEARCH("search"),
	NONE(""),
	INVALID_MOVE("unauthorized_movement");

	private String value;

	private UrnStatus(String aValue) {
		value = aValue;
	}

	public String getValue() {
		return value;
	}
}
