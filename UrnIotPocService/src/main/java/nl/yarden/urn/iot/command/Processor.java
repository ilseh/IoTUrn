/**
 *
 */
package nl.yarden.urn.iot.command;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.yarden.urn.iot.config.AppConfig;
import nl.yarden.urn.iot.http.KpnHttpHandler;

/**
 * Process IoT device commands.
 *
 */
@Component
public class Processor {
	@Autowired
	private AppConfig config;
	@Autowired
	private KpnHttpHandler kpnHandler;

	/**
	 * Commands that can be executed on device.
	 *
	 */
	public static enum Command {
		BUZZ("00"), LIGHT("01"), SYSTEM("02");

		private final String command;

		private Command(String aCommand) {
			command = aCommand;
		}
	}

	/**
	 * Command's arguments.
	 *
	 */
	public static enum CommandArg {
		LIGHT_COLOR_RED("01", Command.LIGHT), LIGHT_COLOR_GREEN("02", Command.LIGHT), LIGHT_COLOR_YELLOW("04", Command.LIGHT),
		LIGHT_ALLWAYS_ON("ff", Command.LIGHT), LIGHT_ALLWAYS_OFF("00ff", Command.LIGHT),
		LIGHT_PATTERN_FOREVER("ff", Command.LIGHT);

		private final String arg;
		private final Command command;

		private CommandArg(String anArg, Command aCommand) {
			arg = anArg;
			command = aCommand;
		}

		/**
		 * @return string containing command and it's argument
		 */
		public String getCommando() {
			return command.command + arg;
		}
	}

	/**
	 * Turn light on.
	 * @param deviceId id of device
	 */
	public void turnLightOn(String deviceId) {
		changeDeviceState(deviceId, CommandArg.LIGHT_ALLWAYS_ON.getCommando());
	}

	/**
	 * Turn light off.
	 * @param deviceId id of device
	 */
	public void turnLightOff(String deviceId) {
		changeDeviceState(deviceId, CommandArg.LIGHT_ALLWAYS_OFF.getCommando());
	}


	/**
	 * Execute command to change device state (ie: turn light on).
	 * @param deviceId deveui of device
	 * @param commando command and args to change state
	 */
	void changeDeviceState(String deviceId, String commando) {
		String uriParams = createUriParams(deviceId, commando);
		String result = kpnHandler.executeCommand(addTokenToUriParams(uriParams));
		if (!"OK".equalsIgnoreCase(result)) {
			throw new RuntimeException("Error sending command to device: " + result);
		}
	}

	/**
	 * Create correct http query param to update state of device.
	 * @param deviceId deveui of device
	 * @param commando command with arguments
	 * @return http query with deviceId and commando and other necessary information
	 */
	String createUriParams(String deviceId, String commando) {
		XMLGregorianCalendar currentDateTime;
		try {
			currentDateTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(TimeZone.getTimeZone("GMT")));
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		return String.format("DevEUI=%s&FPort=%s&Payload=%s&AS_ID=%s&Confirmed=0&Time=%s",
				deviceId, config.getKpnCommandPort(), commando, config.getKpnAsid(), currentDateTime);
	}

	/**
	 * Create the Token parameter value to be used in the http query params.
	 * @param uriParams all uri params to update device state, used to create sha256 encryption on
	 * @return value for token parameter
	 */
	String createUriToken(String uriParams) {
		String sha256Input = uriParams + config.getKpnAeskey().toLowerCase();
		return createSha256(sha256Input);
	}

	/**
	 * Add Token parameter to uri parameters.
	 * @param uriParams uri parameters
	 * @return uri parameter with Token parameter (with value) appended
	 */
	String addTokenToUriParams(String uriParams) {
		return uriParams + "&Token=" + createUriToken(uriParams);
	}

	/**
	 * @param value to encrypt
	 * @return ha256 encryption of value.
	 */
	String createSha256(String value) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(value.getBytes());
		} catch (NoSuchAlgorithmException e1) {
			throw new RuntimeException(e1);
		}
		byte[] digest = md.digest();

		return String.format("%064x", new java.math.BigInteger(1, digest));
	}
}
