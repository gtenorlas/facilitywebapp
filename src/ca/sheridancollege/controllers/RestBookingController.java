package ca.sheridancollege.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
//import ca.sheridancollege.DAO.DAO;
import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Court;

@RestController // specify that this class is a restful controller
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api/booking")
public class RestBookingController {
	// private DAO dao = new DAO();
	CourtDAO courtDAO = new CourtDAO();
	BookingDAO bookingDAO = new BookingDAO();

	public RestBookingController() {
		CorsRegistry registry = new CorsRegistry();
		registry.addMapping("/api/booking").allowedOrigins("*", "http://localhost:8080")
				.allowedMethods("POST", "GET", "PUT", "DELETE").allowedHeaders("Content-Type")
				.exposedHeaders("header-1", "header-2").allowCredentials(false).maxAge(6000);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Booking> getBookingList() {
		return bookingDAO.getAllBookings();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Object getBooking(@PathVariable int id) {
		return bookingDAO.getBookingByID(id);
	}
	
	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public Object getBookings(@PathVariable String email) {
		return bookingDAO.getAllBookings(email);
	}

	// new Booking(id, bookingDate, bookingType, status, startDateTime,
	// endDateTime));
	@RequestMapping(value = "/{customerEmail}/{customerName}/{bookingType}/{status}/{startDateTime}/{endDateTime}/{duration}/{courtId}/{facilityName}", method = {
			RequestMethod.OPTIONS, RequestMethod.POST })
	public Object postBookingListItem(@PathVariable String customerEmail, @PathVariable String customerName,
			@PathVariable String bookingType, @PathVariable String status, @PathVariable String startDateTime,
			@PathVariable String endDateTime, @PathVariable double duration, @PathVariable int courtId,
			@PathVariable String facilityName) {

		// DateTimeFormatter FMT = new
		// DateTimeFormatterBuilder().appendPattern("MM-dd-yyyy-HH-mm")
		// .parseDefaulting(ChronoField.NANO_OF_DAY,
		// 0).toFormatter().withZone(ZoneId.of("America/Toronto"));

		LocalDateTime bookingDateTime = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm");
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

		Booking booking = new Booking(customerName, bookingDateTime, bookingType, status, startDateTimeLocal,
				endDateTimeLocal, duration, null);

		booking.setCustomerEmail(customerEmail);
		booking.setFacilityName(facilityName);

		// bookingDAO.saveBooking(booking);

		if (bookingDAO.bookingValidation(courtId, startDateTimeLocal, endDateTimeLocal)) {
			// Court court = courtDAO.getCourt(courtId);
			Court court = courtDAO.getCourt(courtId);
			booking.setCourt(court);
			// court.getBookings().add(booking); // add the booking to the particular court
			// courtDAO.saveCourt(court);
			int id = bookingDAO.saveBookingForAPI(booking);
			if (id != 0) {
				return id;
			} else {
				return "Internal error while booking save";
			}
		} else {
			return "Court not available";
		}

	}

}
