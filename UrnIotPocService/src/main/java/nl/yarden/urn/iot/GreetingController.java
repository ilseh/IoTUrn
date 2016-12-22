package nl.yarden.urn.iot;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.yarden.urn.iot.beans.IotRequest;


@RestController
public class GreetingController {
	@Autowired
	private IotRepository repository;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(path="/devdata", method = RequestMethod.POST)
    public void test(@RequestBody IotRequest test) {
    	System.out.print(test);
    	repository.save(test.getDevEui_uplink());
    }
}
