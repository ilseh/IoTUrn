/**
 *
 */
package nl.yarden.urn.iot.model.iot.http;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import nl.yarden.urn.iot.config.AppConfig;

/**
 * Execute http(s) calls to KPN webservice.
 */
@Component
public class KpnHttpHandler {
	private static final Logger LOG = LoggerFactory.getLogger(KpnHttpHandler.class);
	@Autowired
	private AppConfig config;

	/**
	 * Call kpn webservice to execute command on device.
	 * @param params query params in uri, used in the http post
	 * @return http statuscode
	 */
	public String executeCommand(String params) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("", null);
		ResponseEntity<String> response = null;
		String uri = config.getKpnLoraUrl() + "?" + params;
		try {
			response = restTemplate.exchange(new URI(uri), HttpMethod.POST, request, String.class);
			LOG.debug("POSTED to: " + uri);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		String responseStatus = "";
		if (response != null & response.getStatusCode() != null) {
			responseStatus = response.getStatusCode().getReasonPhrase();
		}
		return responseStatus;
	}

}
