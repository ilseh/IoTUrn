/**
 *
 */
package nl.yarden.urn.iot.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import nl.yarden.urn.iot.config.AppConfig;
import nl.yarden.urn.iot.model.iot.Processor;
import nl.yarden.urn.iot.model.iot.http.KpnHttpHandler;



/**
 * Test {@link Processor}.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessorTest {
	@InjectMocks
	private Processor processor;
	@Mock
	private AppConfig configMock;
	@Mock
	private KpnHttpHandler kpnHttpHandlerMock;
	@Captor
	private ArgumentCaptor<String> kpnHandlerArgCaptor;

	/**
	 * Setup.
	 */
	@Before
	public void setUp() {
		when(configMock.getKpnCommandPort()).thenReturn("119");
		when(configMock.getKpnAeskey()).thenReturn("B9E2547747DEB3A59671D042B5361CF3");
		when(configMock.getKpnAsid()).thenReturn("appfactory");
	}

	/**
	 * Test createSh265. With testdata/example received from KPN.
	 */
	@Test
	public void testCreateSha265() {
		String input = "DevEUI=0059AC0014300E17&FPort=119&Payload=00ff&AS_ID=appfactory&Confirmed=0&Time=2017-01-03T09:43:27" + "B9E2547747DEB3A59671D042B5361CF3".toLowerCase();
		String expected = "b91b8d27a8d7b724530084c3b90e78be6d0b029800d93bdb55ccc01a6479ee16";
		String result = processor.createSha256(input);
		assertEquals(expected, result);
	}

	/**
	 * Test turnLightOn.
	 */
	@Test
	public void testTurnLightOn() {
		String deviceId = "0059AC0014300E16";

		when(kpnHttpHandlerMock.executeCommand(anyString())).thenReturn("OK");
		processor.turnLightOn(deviceId);

		verify(kpnHttpHandlerMock).executeCommand(kpnHandlerArgCaptor.capture());
		assertTrue(kpnHandlerArgCaptor.getValue().matches(
				"DevEUI=0059AC0014300E16&FPort=119&Payload=01010a0aff&AS_ID=appfactory&Confirmed=1&Time=\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z&Token=.{64}"));
	}


	/**
	 * Test turnLightOff.
	 */
	@Test
	public void testTurnLightOff() {
		String deviceId = "0059AC0014300E16";

		when(kpnHttpHandlerMock.executeCommand(anyString())).thenReturn("OK");
		processor.turnLightOff(deviceId);

		verify(kpnHttpHandlerMock).executeCommand(kpnHandlerArgCaptor.capture());
		assertTrue(kpnHandlerArgCaptor.getValue().matches(
				"DevEUI=0059AC0014300E16&FPort=119&Payload=0101000000&AS_ID=appfactory&Confirmed=1&Time=\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z&Token=.{64}"));
	}
}
