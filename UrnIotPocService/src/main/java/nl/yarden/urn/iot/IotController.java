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
import nl.yarden.urn.iot.ui.Helper;
import nl.yarden.urn.iot.ui.ListForm;
import nl.yarden.urn.iot.ui.UrnForm;

/**
 * Controller.
 *
 */
@RestController
public class IotController {
	private static final Logger LOG = LoggerFactory.getLogger(IotController.class);
	private final static String OVERVIEW = "gui/overview";
	private final static String VIEW_URNS = "gui/viewUrns";
	private final static String ADMIN = "gui/admin";
	private final static String DECEASED = "gui/deceased";
	@Autowired
	private DevEuiRepository repository;
	@Autowired
	private Helper helper;

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
			helper.saveUrn(urnForm.getUrn());
		} catch (DataIntegrityViolationException ex) {
			urnForm.setError(ex.getMostSpecificCause().getMessage());
		}
		return createDefaultModelView(new ModelAndView(ADMIN, "urnForm", urnForm));
	}

	/**
	 * Administer IoT device on urn.
	 * @return view for administering urn
	 */
	@RequestMapping(path="/viewUrns", method = RequestMethod.GET)
	public ModelAndView viewUrns(@ModelAttribute ListForm<Urn> urnsForm, @RequestParam(required = false) String deceasedLastName) {
		LOG.debug("View urns");
		PagedListHolder<Urn> urnsPagedList = null;
		if (deceasedLastName != null) {
			urnsPagedList = new PagedListHolder<>(helper.getDeceasedUrns(deceasedLastName));
		} else {
			urnsPagedList = new PagedListHolder<>(helper.getAllUrns());
		}
		urnsForm = new ListForm<>(urnsPagedList);
		urnsForm.setColumnHeaders(Arrays.asList("ID", "Overledene", "Intern referentie id", "Urn Id", "Datum laatste beweging"));
		return createDefaultModelView(new ModelAndView(VIEW_URNS, "viewUrnsForm", urnsForm));
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
	@RequestMapping(path="/viewEvents")
	public ModelAndView viewEvents(@RequestParam(required = true) String urnTag) {
		LOG.debug("Viewing urn events");
		PagedListHolder<DevEUI_uplink> eventsPagedList = new PagedListHolder<>(helper.getAllEvents(urnTag));
		eventsPagedList.setSort(new MutableSortDefinition("time", true, false));
		eventsPagedList.resort();
		ListForm<DevEUI_uplink> eventsForm = new ListForm<>(eventsPagedList);
		eventsForm.setColumnHeaders(Arrays.asList("ID", "Urn tag", "Payload", "Tijdstip beweging"));

		return createDefaultModelView(new ModelAndView(OVERVIEW, "eventsForm", eventsForm));
	}

	/**
	 * Endpoint to search deceased.
	 * @param request with event
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
	 * Hello
	 * @return
	 */
	@RequestMapping(path="/hello")
	public @ResponseBody String hello() {
		LOG.debug("hello");
		return "hello";
	}

	ModelAndView createDefaultModelView(ModelAndView mv) {
		//        mv.addObject(CONFIG, config);
		return mv;
	}
}
