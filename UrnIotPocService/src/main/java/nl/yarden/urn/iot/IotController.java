package nl.yarden.urn.iot;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import nl.yarden.urn.iot.beans.DevEUI_uplink;
import nl.yarden.urn.iot.beans.IotRequest;
import nl.yarden.urn.iot.beans.Urn;
import nl.yarden.urn.iot.ui.DeceasedForm;
import nl.yarden.urn.iot.ui.DeviceActions;
import nl.yarden.urn.iot.ui.RepositoryActions;
import nl.yarden.urn.iot.ui.ListForm;
import nl.yarden.urn.iot.ui.UrnForm;

/**
 * Controller web interface.
 *
 */
@RestController
public class IotController {
	private static final Logger LOG = LoggerFactory.getLogger(IotController.class);
	private final static String OVERVIEW = "overview";
	private final static String VIEW_URNS = "viewUrns";
	private final static String ADMIN = "admin";
	private final static String DECEASED = "deceased";
	@Autowired
	private RepositoryActions repositories;
	@Autowired
	private DeviceActions deviceCommands;

	/**
	 * Administer IoT device on urn.
	 * @return view for administering urn
	 */
	@RequestMapping(path="/admin", method = RequestMethod.GET)
	public ModelAndView admin(@ModelAttribute UrnForm urnForm) {
		LOG.debug("Admin urn");
		return createDefaultModelView(new ModelAndView(ADMIN, "urnForm", urnForm));
	}

	/**
	 * Administer IoT device on urn. Save new urn IoT config.
	 * @return view with urn event information
	 */
	@RequestMapping(path="/admin", method = RequestMethod.POST)
	public ModelAndView saveUrnConfig(@ModelAttribute UrnForm urnForm) {
		LOG.debug("Admin urn");
		try {
			repositories.saveUrn(urnForm.getUrn());
		} catch (DataIntegrityViolationException ex) {
			urnForm.setError(ex.getMostSpecificCause().getMessage());
		}
		return createDefaultModelView(new ModelAndView(ADMIN, "urnForm", urnForm));
	}

	/**
	 * View configured urns.
	 * @param urnsForm form that can contain list of urns
	 * @param deceasedLastName lastname of deceased, only that urns of persons with that lastname are fetched
	 * @return view with urns
	 */
	@RequestMapping(path="/viewUrns", method = RequestMethod.GET)
	public ModelAndView viewUrns(@ModelAttribute ListForm<Urn> urnsForm, @RequestParam(required = false) String deceasedLastName,
			@RequestParam(required = false) String deviceId, @RequestParam(required = false) String action) {
		LOG.debug("View urns");
		PagedListHolder<Urn> urnsPagedList = null;
		if (StringUtils.isNotBlank(deceasedLastName)) {
			urnsPagedList = new PagedListHolder<>(repositories.getDeceasedUrns(deceasedLastName));
		} else {
			urnsPagedList = new PagedListHolder<>(repositories.getAllUrns());
		}
		urnsPagedList.getSource().stream().forEach(urn -> {if (urn.getDevEUI().equalsIgnoreCase(deviceId)) {urn.setCurrentAction(action);}});
		urnsForm = new ListForm<>(urnsPagedList);
		urnsForm.setColumnHeaders(Arrays.asList("ID", "Overledene", "Intern referentie id", "Urn Id", "Datum laatste beweging", "", ""));
		return createDefaultModelView(new ModelAndView(VIEW_URNS, "viewUrnsForm", urnsForm));
	}


	/**
	 * Endpoint to receive events from the IoT device on the urn.
	 * @param request with event
	 */
	@RequestMapping(path="/devdata", method = RequestMethod.POST)
	public void storeIotRequest(@RequestBody IotRequest request) {
		LOG.debug(String.format("received request: %s", request));
		repositories.saveUrnEvent(request.getDevEui_uplink());
	}

	/**
	 * View events on urn.
	 * @param urnTag id/tag of urn to view event info for
	 * @return view with urn events information
	 */
	@RequestMapping(path="/viewEvents")
	public ModelAndView viewEvents(@RequestParam(required = true) String urnTag) {
		LOG.debug("Viewing urn events");
		PagedListHolder<DevEUI_uplink> eventsPagedList = new PagedListHolder<>(repositories.getAllEvents(urnTag));
		eventsPagedList.setSort(new MutableSortDefinition("time", true, false));
		eventsPagedList.resort();
		ListForm<DevEUI_uplink> eventsForm = new ListForm<>(eventsPagedList);
		eventsForm.setColumnHeaders(Arrays.asList("ID", "Urn tag", "Payload", "Tijdstip beweging"));

		return createDefaultModelView(new ModelAndView(OVERVIEW, "eventsForm", eventsForm));
	}

	/**
	 * Endpoint to search deceased person.
	 * @param searchLastName search on basis of lastname, if none is specified, all deceaseds are returned
	 */
	@RequestMapping(path="/deceased")
	public ModelAndView deceased(@RequestParam(required = false) String searchLastName) {
		LOG.debug("Viewing deceased");
		if (StringUtils.isNoneBlank(searchLastName)) {
			return createDefaultModelView(new ModelAndView(new RedirectView("/viewUrns?deceasedLastName=" + searchLastName)));
		} else {
			return createDefaultModelView(new ModelAndView(DECEASED, "deceasedForm", new DeceasedForm()));
		}
	}

	/**
	 * Hello world, used in health check Kubernetes.
	 * @return arbitrary string.
	 */
	@RequestMapping(path="/hello")
	public @ResponseBody String hello() {
		LOG.debug("hello");
		return "hello there";
	}

	/**
	 * Hello world, used in health check Kubernetes.
	 * @return arbitrary string.
	 */
	@RequestMapping(path="/lightUrn")
	public ModelAndView lightUrn(@RequestParam String deviceId, @RequestParam boolean on, @RequestParam(required = false) String deceasedLastName) {
		String action = on ? "on" : "off";
		LOG.debug("Turn light {} for device: {}", action, deviceId);
		deviceCommands.turnLight(on, deviceId);

		return createDefaultModelView(new ModelAndView(new RedirectView(
				String.format("/viewUrns?deviceId=%s&action=turned_light_%s&deceasedLastName=%s", deviceId, action, deceasedLastName))));
	}

	/**
	 * Method that creates default modelAndView. Useful in case we do standard operations on it.
	 * @param mv default modelAndView
	 * @return default modelAndView
	 */
	ModelAndView createDefaultModelView(ModelAndView mv) {
		//        mv.addObject(CONFIG, config);
		return mv;
	}
}
