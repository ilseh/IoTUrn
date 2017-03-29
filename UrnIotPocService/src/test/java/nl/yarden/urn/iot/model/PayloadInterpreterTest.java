/**
 *
 */
package nl.yarden.urn.iot.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test of {@link PayloadInterpreter}.
 *
 */
public class PayloadInterpreterTest {
	private PayloadInterpreter payloadInterpreter = new PayloadInterpreter();

	/**
	 * Test isMovement.
	 */
	@Test
	public void testIsMovement() {
		assertFalse(payloadInterpreter.isMovement("240000078c000000000000000000ff4336e4eb00000000000001802e02"));
		assertTrue(payloadInterpreter.isMovement("2400000058000000000000000000ff4fff3ef40000000000000191ef01"));
	}

	/**
	 * Test getIoFlagsHex.
	 */
	@Test
	public void testGetIoFlagsHex() {
		assertEquals("43", payloadInterpreter.getIoFlagsHex("240000078c000000000000000000ff4336e4eb00000000000001802e02"));
		assertEquals("4f", payloadInterpreter.getIoFlagsHex("2400000058000000000000000000ff4fff3ef40000000000000191ef01"));
	}

	/**
	 * Test rightSubstring.
	 */
	@Test
	public void testRightSubstring() {
		String subject = "1234567";
		assertEquals("7", payloadInterpreter.rightSubstring(subject, 0, 1));
		assertEquals("4", payloadInterpreter.rightSubstring(subject, 3, 1));
		assertEquals("12", payloadInterpreter.rightSubstring(subject, 5, 2));
		assertEquals(subject, payloadInterpreter.rightSubstring(subject, 0, 7));
	}
}
