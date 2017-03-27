package nl.yarden.urn.iot.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Data we receive form IoT platform and what we want to store in the db.
 *
 */
@Entity
public class DevEUI_uplink {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "time", columnDefinition = "timestamp with time zone")
	private Date time;

	private String payload_hex;

	private String DevEUI;

	private String status;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}


	/**
	 * @return the payload_hex
	 */
	public String getPayload_hex() {
		return payload_hex;
	}

	/**
	 * @param payload_hex the payload_hex to set
	 */
	public void setPayload_hex(String payload_hex) {
		this.payload_hex = payload_hex;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
