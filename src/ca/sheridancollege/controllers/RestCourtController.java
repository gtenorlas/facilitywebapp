package ca.sheridancollege.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import ca.sheridancollege.DAO.BookingDAO;
import ca.sheridancollege.DAO.CourtDAO;
import ca.sheridancollege.DAO.FacilityDAO;
//import ca.sheridancollege.DAO.DAO;
import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Court;
import ca.sheridancollege.beans.Facility;

@RestController // specify that this class is a restful controller
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api/court")
public class RestCourtController {
	// private DAO dao = new DAO();
	CourtDAO courtDAO = new CourtDAO();
	BookingDAO bookingDAO = new BookingDAO();
	FacilityDAO facilityDAO = new FacilityDAO();

	public RestCourtController() {
		CorsRegistry registry = new CorsRegistry();
		registry.addMapping("/api/court").allowedOrigins("*", "http://localhost:8080")
				.allowedMethods("POST", "GET", "PUT", "DELETE").allowedHeaders("Content-Type")
				.exposedHeaders("header-1", "header-2").allowCredentials(false).maxAge(6000);
	}



	// new Booking(id, bookingDate, bookingType, status, startDateTime,
	// endDateTime));
	@RequestMapping(value = "/{facilityId}/{startDateTime}/{endDateTime}", method = {
			RequestMethod.OPTIONS, RequestMethod.POST })
	public Object getCourts(@PathVariable int facilityId, @PathVariable String startDateTime, @PathVariable String endDateTime) {
		List<Court> courts = new ArrayList<Court>();
		

		LocalDateTime bookingDate = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-H-mm");
		LocalDateTime startDateTimeLocal = null, endDateTimeLocal = null;

		// try to convert the string date into localdatetime
		try {
			startDateTimeLocal = LocalDateTime.parse(startDateTime, formatter);
			endDateTimeLocal = LocalDateTime.parse(endDateTime, formatter);
		} catch (Exception e) {
			return "Please check date format entered.";
		}

		if (endDateTimeLocal.isBefore(startDateTimeLocal) || endDateTimeLocal.isEqual(startDateTimeLocal)) {
			return "Booking start date and time must be ahead than end date and time.";
		}

		Facility facility = facilityDAO.getFacility(facilityId);
		
		for (Court eachCourt: facility.getCourts()) {
			if (eachCourt.getAvailability().equals("Active")) {
				if (bookingDAO.bookingValidation(eachCourt.getCourtNumber(), startDateTimeLocal, endDateTimeLocal)) {
					courts.add(eachCourt);
				} 
			}
		}
		return courts;

	}



}
