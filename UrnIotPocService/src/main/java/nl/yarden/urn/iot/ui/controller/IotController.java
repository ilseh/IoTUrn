package nl.yarden.urn.iot.ui.controller;

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
import nl.yarden.urn.iot.model.DataTransformer;
import nl.yarden.urn.iot.model.EventHandler;
import nl.yarden.urn.iot.model.IotModel;
import nl.yarden.urn.iot.ui.DeceasedForm;
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
	private final static String DELETE_URN = "delete/urn";
	@Autowired
	private IotModel model;
	@Autowired
	private EventHandler eventHandler;
	@Autowired
	private DataTransformer dataTransformer;

	/**
	 * Administer IoT device on urn.
	 * @return view for administering urn
	 */
	@RequestMapping(path="/" + ADMIN, method = RequestMethod.GET)
	public ModelAndView admin(@ModelAttribute UrnForm urnForm) {
		LOG.debug("Admin urn");
		return createDefaultModelView(new ModelAndView(ADMIN, "urnForm", urnForm));
	}

	/**
	 * Administer IoT device on urn. Save new urn IoT config.
	 * @return view with urn event information
	 */
	@RequestMapping(path="/" + ADMIN, method = RequestMethod.POST)
	public ModelAndView saveUrnConfig(@ModelAttribute UrnForm urnForm) {
		LOG.debug("Admin urn");
		try {
			model.saveUrn(urnForm.getUrn());
		} catch (DataIntegrityViolationException ex) {
			urnForm.setError(ex.getMostSpecificCause().getMessage());
		}
		return createDefaultModelView(new ModelAndView(new RedirectView("/" + VIEW_URNS)));
	}

	/**
	 * Delete urn.
	 * @param urnId to delete
	 */
	@RequestMapping(path="/" + DELETE_URN)
	public ModelAndView deleteUrn(@RequestParam String deviceId) {
		model.deleteUrn(deviceId);
		return createDefaultModelView(new ModelAndView(new RedirectView("/" + VIEW_URNS)));
	}

	/**
	 * View configured urns.
	 * @param urnsForm form that can contain list of urns
	 * @param deceasedLastName lastname of deceased, only that urns of persons with that lastname are fetched
	 * @return view with urns
	 */
	@RequestMapping(path="/" + VIEW_URNS, method = RequestMethod.GET)
	public ModelAndView viewUrns(@ModelAttribute ListForm<Urn> urnsForm, @RequestParam(required = false) String deceasedLastName,
			@RequestParam(required = false) String deviceId, @RequestParam(required = false) String status) {
		LOG.debug("View urns");
		PagedListHolder<Urn> urnsPagedList = null;
		if (StringUtils.isNotBlank(deceasedLastName)) {
			urnsPagedList = new PagedListHolder<>(model.getDeceasedUrns(deceasedLastName));
		} else {
			urnsPagedList = new PagedListHolder<>(model.getAllUrns(), new MutableSortDefinition("deceasedLastName", false, false));
		}
		urnsPagedList.setSort(new MutableSortDefinition("deceasedLastName", true, true));
		urnsPagedList.resort();
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
		if (request.getDevEui_uplink() == null) {
			LOG.warn("Received null DevEUI_uplink, this should not happen!");
		} else {
			LOG.debug(String.format("received request for %s on time %s, payload %s", request.getDevEui_uplink().getDateTime(),
																			   		  request.getDevEui_uplink().getDevEUI(), 
																			   		  request.getDevEui_uplink().getPayload_hex()));
			dataTransformer.correctTime(request.getDevEui_uplink());
			dataTransformer.setEvent(request.getDevEui_uplink());
			model.saveUrnEvent(request.getDevEui_uplink());
			eventHandler.handleEvent(request.getDevEui_uplink());
		}
	}

	/**
	 * View events on urn.
	 * @param urnTag id/tag of urn to view event info for
	 * @return view with urn events information
	 */
	@RequestMapping(path="/viewEvents")
	public ModelAndView viewEvents(@RequestParam(required = true) String urnTag, 
			@RequestParam(required = false) Integer currentPage, @RequestParam(required = false) String gotoPage) {
		LOG.debug("Viewing urn events");
		PagedListHolder<DevEUI_uplink> eventsPagedList = new PagedListHolder<>(model.getAllEvents(urnTag));
		eventsPagedList.setPageSize(50);
		eventsPagedList.setSort(new MutableSortDefinition("dateTime", true, false));
		eventsPagedList.resort();
		eventsPagedList.setPage(currentPage == null? 0 : currentPage);
		ListForm<DevEUI_uplink> eventsForm = new ListForm<>(eventsPagedList);
		eventsForm.setColumnHeaders(Arrays.asList("ID", "Urn tag", "Payload", "Type event", "Tijdstip"));
		
		if ("next".equalsIgnoreCase(gotoPage)) {
			eventsPagedList.nextPage();
		} 

		return createDefaultModelView(new ModelAndView(OVERVIEW, "eventsForm", eventsForm));
	}

	/**
	 * Endpoint to search deceased person.
	 * @param searchLastName search on basis of lastname, if none is specified, all deceaseds are returned
	 */
	@RequestMapping(path="/" + DECEASED)
	public ModelAndView deceased(@RequestParam(required = false) String searchLastName) {
		LOG.debug("Viewing deceased");
		if (StringUtils.isNoneBlank(searchLastName)) {
			return createDefaultModelView(new ModelAndView(
					new RedirectView(String.format("/%s?deceasedLastName=%s", VIEW_URNS, searchLastName))));
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
		model.turnLight(on, deviceId);
		return createDefaultModelView(new ModelAndView(new RedirectView(
				String.format("/%s?deviceId=%s&deceasedLastName=%s", VIEW_URNS, deviceId, deceasedLastName))));
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
