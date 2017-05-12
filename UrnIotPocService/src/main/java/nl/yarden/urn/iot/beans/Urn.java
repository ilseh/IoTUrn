/**
 *
 */
package nl.yarden.urn.iot.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;

import nl.yarden.urn.iot.model.UrnStatus;

/**
 * Urn.
 *
 */
@Entity
public class Urn {
	@Id
	@GeneratedValue
	private Long id;

	// IoT device id of urn.
	@Column(unique=true)
	private String DevEUI;

	// Internal Yarden adminstrative Id of the urn.
	private String referenceId;
	// Name of the deceased person in the urn.
	private String deceasedFirstName;
	private String deceasedLastName;
	private String currentStatus;

	@Formula("(SELECT max(d.time) FROM deveui_uplink d WHERE d.DevEUI = DevEUI and d.event_type='MOVE')" )
	private Date dateLch;

	/**
	 * @return the deceasedFirstName
	 */
	public String getDeceasedFirstName() {
		return deceasedFirstName;
	}
	/**
	 * @return the deceasedLastName
	 */
	public String getDeceasedLastName() {
		return deceasedLastName;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the referenceId
	 */
	public String getReferenceId() {
		return referenceId;
	}
	/**
	 * @param deceasedFirstName the deceasedFirstName to set
	 */
	public void setDeceasedFirstName(String deceasedFirstName) {
		this.deceasedFirstName = deceasedFirstName;
	}
	/**
	 * @param deceasedLastName the deceasedLastName to set
	 */
	public void setDeceasedLastName(String deceasedLastName) {
		this.deceasedLastName = deceasedLastName;
	}
	/**
	 * @return the devEUI
	 */
	public String getDevEUI() {
		return DevEUI;
	}
	/**
	 * @param devEUI the devEUI to set
	 */
	public void setDevEUI(String devEUI) {
		DevEUI = devEUI;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	/**
	 * @return the dateLch
	 */
	public Date getDateLch() {
		return this.dateLch;
	}
	/**
	 * @param dateLch the dateLch to set
	 */
	public void setDateLch(Date dateLch) {
		this.dateLch = dateLch;
	}
	/**
	 * @return the currentStatus
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}
	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(UrnStatus currentStatus) {
		this.currentStatus = currentStatus.getValue();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getDevEUI() + " - " + getDeceasedLastName() + "(" + getDeceasedFirstName() + ")";
	}

}
