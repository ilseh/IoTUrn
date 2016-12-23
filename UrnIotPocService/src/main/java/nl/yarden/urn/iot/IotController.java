package nl.yarden.urn.iot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.yarden.urn.iot.beans.IotRequest;

/**
 * Controller.
 *
 */
@RestController
public class IotController {
	private static final Logger LOG = LoggerFactory.getLogger(IotController.class);
	@Autowired
	private IotRepository repository;
    
    @RequestMapping(path="/devdata", method = RequestMethod.POST)
    public void storeIotRequest(@RequestBody IotRequest request) {
    	LOG.debug(String.format("received request: %s", request));
    	repository.save(request.getDevEui_uplink());
    }
}
