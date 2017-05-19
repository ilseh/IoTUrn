package nl.yarden.urn.iot.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import nl.yarden.urn.iot.beans.DevEUI_uplink;

public class DataTransformerTest {
	private DataTransformer transformer = new DataTransformer();

	/**
	 * Test correctTime.
	 */
	@Test
	public void testCorrectTime() {
		String incorrectDate = "2017-05-11T09:49:24.4311+02:00";
		DevEUI_uplink link = new DevEUI_uplink();
		link.setTime(incorrectDate);
		transformer.correctTime(link);
		assertNotNull(link.getDateTime());
	}

}
