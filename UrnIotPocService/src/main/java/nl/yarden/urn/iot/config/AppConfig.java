/**
 *
 */
package nl.yarden.urn.iot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application config.
 *
 */
@Configuration
@ConfigurationProperties(prefix = "appConfig")
public class AppConfig {
	private String kpnAsid;
	private String kpnAeskey;
	private String kpnLoraUrl;
	private String kpnCommandPort;
	/**
	 * @return the kpnAsid
	 */
	public String getKpnAsid() {
		return kpnAsid;
	}
	/**
	 * @param kpnAsid the kpnAsid to set
	 */
	public void setKpnAsid(String kpnAsid) {
		this.kpnAsid = kpnAsid;
	}
	/**
	 * @return the kpnAeskey
	 */
	public String getKpnAeskey() {
		return kpnAeskey;
	}
	/**
	 * @param kpnAeskey the kpnAeskey to set
	 */
	public void setKpnAeskey(String kpnAeskey) {
		this.kpnAeskey = kpnAeskey;
	}
	/**
	 * @return the kpnLoraUrl
	 */
	public String getKpnLoraUrl() {
		return kpnLoraUrl;
	}
	/**
	 * @param kpnLoraUrl the kpnLoraUrl to set
	 */
	public void setKpnLoraUrl(String kpnLoraUrl) {
		this.kpnLoraUrl = kpnLoraUrl;
	}
	/**
	 * @return the kpnCommandPort
	 */
	public String getKpnCommandPort() {
		return kpnCommandPort;
	}
	/**
	 * @param kpnCommandPort the kpnCommandPort to set
	 */
	public void setKpnCommandPort(String kpnCommandPort) {
		this.kpnCommandPort = kpnCommandPort;
	}


}
