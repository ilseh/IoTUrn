package nl.yarden.urn.iot;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import nl.yarden.urn.iot.beans.DevEUI_uplink;
import nl.yarden.urn.iot.beans.IotRequest;
import nl.yarden.urn.iot.ui.EventsForm;
import nl.yarden.urn.iot.ui.Helper;
import nl.yarden.urn.iot.ui.UrnForm;

/**
 * Controller.
 *
 */
@RestController
public class IotController {
	private static final Logger LOG = LoggerFactory.getLogger(IotController.class);
	private final static String OVERVIEW = "gui/overview";
	private final static String ADMIN = "gui/admin";
	@Autowired
	private DevEuiRepository repository;
	@Autowired
	private Helper helper;

	/**
	 * Administer IoT device on urn.
	 * @return view with urn event information
	 */
	@RequestMapping(path="/admin")
	public ModelAndView admin(@ModelAttribute UrnForm urnForm) {
		LOG.debug("Admin urn");

		return createDefaultModelView(new ModelAndView(ADMIN, "urnForm", urnForm));
	}

	ModelAndView createDefaultModelView(ModelAndView mv) {
		//        mv.addObject(CONFIG, config);
		return mv;
	}

	/**
	 * Endpoint to receive events from the IoT device on the urn.
	 * @param request with event
	 */
	@RequestMapping(path="/devdata", method = RequestMethod.POST)
	public void storeIotRequest(@RequestBody IotRequest request) {
		LOG.debug(String.format("received request: %s", request));
		repository.save(request.getDevEui_uplink());
	}

	/**
	 * View events on urn.
	 * @return view with urn event information
	 */
	@RequestMapping(path="/overview")
	public ModelAndView view() {
		LOG.debug("Viewing urns");
		PagedListHolder<DevEUI_uplink> eventsPagedList = new PagedListHolder<>(helper.getAllEvents());
		EventsForm eventsForm = new EventsForm(eventsPagedList);
		eventsForm.setColumnHeaders(Arrays.asList("ID", "Urn tag", "Payload", "Time"));

		return createDefaultModelView(new ModelAndView(OVERVIEW, "eventsForm", eventsForm));
	}
}
