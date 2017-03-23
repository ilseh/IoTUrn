package nl.yarden.urn.iot.config;

/**
 * Smtp config.
 */
public class SmtpConfig {
	private String host;
	private String username;
	private String password;
	private boolean isSecure;
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param aHost the host to set
	 */
	public void setHost(String aHost) {
		host = aHost;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param aUsername the username to set
	 */
	public void setUsername(String aUsername) {
		username = aUsername;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param aPassword the password to set
	 */
	public void setPassword(String aPassword) {
		password = aPassword;
	}
	/**
	 * @return the isSecure
	 */
	public boolean isSecure() {
		return isSecure;
	}
	/**
	 * @param aIsSecure the isSecure to set
	 */
	public void setIsSecure(boolean aIsSecure) {
		isSecure = aIsSecure;
	}

}
