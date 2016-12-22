package nl.yarden.urn.iot.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.datatype.XMLGregorianCalendar;

@Entity
public class DevEUI_uplink {
	@Id
	@GeneratedValue
	private Long id;
	
//	private XMLGregorianCalendar time;
	
	private String payload_hex;
	
	private String DevEUI;
//
//	@Transient
//	public XMLGregorianCalendar getTime() {
//		return time;
//	}
//
//	public void setTime(XMLGregorianCalendar time) {
//		this.time = time;
//	}

	public String getPayload_hex() {
		return payload_hex;
	}

	public void setPayload_hex(String payload_hex) {
		this.payload_hex = payload_hex;
	}

	public String getDevEUI() {
		return DevEUI;
	}

	public void setDevEUI(String devEUI) {
		DevEUI = devEUI;
	}
	
}
