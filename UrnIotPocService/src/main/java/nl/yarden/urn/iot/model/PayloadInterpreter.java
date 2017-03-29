/**
 *
 */
package nl.yarden.urn.iot.model;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

/**
 * Interpreter of payload.
 *
 */
@Component
public class PayloadInterpreter {
	private static final int IO_FLAGS_INDEX = 30;
	private static final int IO_FLAGS_SIZE = 2;
	private static final String IS_MOVING = "1";
	private static final int COPY_OF_IO3_INDEX = 3; // (from the right of String of bits)

	/**
	 * @param payload of io device
	 * @return true if payload contains notification of movement
	 */
	public boolean isMovement(String payload) {
		String ioFlagsHex = payload.substring(IO_FLAGS_INDEX, IO_FLAGS_INDEX + IO_FLAGS_SIZE);
		String ioFlagsBits = fromHexToBinary(ioFlagsHex);
		return IS_MOVING.equals(rightSubstring(ioFlagsBits, COPY_OF_IO3_INDEX, IS_MOVING.length()));
	}

	/**
	 * @param subject string
	 * @param indexFromRight start position from the end of subject to look for substring (0 based)
	 * @param subStringSize size of substring
	 * @return subString
	 */
	String rightSubstring(String subject, int indexFromRight, int subStringSize) {
		int indexInterest = subject.length() - indexFromRight;
		return subject.substring(indexInterest - subStringSize, indexInterest);
	}

	/**
	 * @param payload of interest (in hex)
	 * @return part of payload that contains hex for io_flags
	 */
	String getIoFlagsHex(String payload) {
		return payload.substring(IO_FLAGS_INDEX, IO_FLAGS_INDEX + IO_FLAGS_SIZE);
	}

	private String fromHexToBinary(String hex) {
		return new BigInteger(hex, 16).toString(2);
	}
}
