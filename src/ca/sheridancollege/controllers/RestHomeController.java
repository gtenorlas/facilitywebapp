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
@RequestMapping("/api")
public class RestHomeController {
	// private DAO dao = new DAO();
	CourtDAO courtDAO = new CourtDAO();
	BookingDAO bookingDAO = new BookingDAO();

	public RestHomeController() {
		CorsRegistry registry = new CorsRegistry();
		registry.addMapping("/api").allowedOrigins("*", "http://localhost:8080")
				.allowedMethods("POST", "GET", "PUT", "DELETE").allowedHeaders("Content-Type")
				.exposedHeaders("header-1", "header-2").allowCredentials(false).maxAge(6000);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET) // routing to home
	public String home(Model model) {
		return "home";

	}

	@RequestMapping(value = "/bookingList", method = RequestMethod.GET)
	public List<Booking> getBookingList() {
		return bookingDAO.getAllBookings();
	}

	@RequestMapping(value = "/bookingList/{id}", method = RequestMethod.GET)
	public Object getBookingList(@PathVariable int id) {
		return bookingDAO.getBookingByID(id);
	}

	@RequestMapping(value = "/bookingList/{courtId}", method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public String postBookingListItem(@PathVariable int courtId) {
		return "it works" + courtId;
	}

	// new Booking(id, bookingDate, bookingType, status, startDateTime,
		// endDateTime));
		@RequestMapping(value = "/bookingList/{customerEmail}/{customerName}/{bookingType}/{status}/{startDateTime}/{endDateTime}/{duration}/{courtId}/{paymentId}", method = {
				RequestMethod.OPTIONS, RequestMethod.POST })
		public String postBookingListItem(@PathVariable String customerEmail,@PathVariable String customerName, @PathVariable String bookingType,
				@PathVariable String status, @PathVariable String startDateTime, @PathVariable String endDateTime,
				@PathVariable double duration, @PathVariable int courtId, @PathVariable int paymentId) {

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

			// bookingDAO.saveBooking(booking);

			if (bookingDAO.bookingValidation(courtId, startDateTimeLocal, endDateTimeLocal)) {
				// Court court = courtDAO.getCourt(courtId);
				Court court = courtDAO.getCourt(courtId);
				booking.setCourt(court);
				court.getBookings().add(booking); // add the booking to the particular court

				courtDAO.saveCourt(court);
				return "Booking Saved";
			} else {
				return "Court " + courtId + " is not available with the selected dates and time";
			}

		}

	/*
	 * // @RequestMapping(value="/studentList", method=RequestMethod.PUT, //
	 * headers={"Contenttype=application/json"})
	 * 
	 * @RequestMapping(value = "/bookingList", method = RequestMethod.PUT) public
	 * void putBookingtList(@RequestBody List<Booking> bookingList) {
	 * dao.putBookingList(bookingList); }
	 * 
	 * @RequestMapping(value = "/bookingList/{id}", method = RequestMethod.PUT)
	 * public String putBooking(@PathVariable int id, @RequestBody Booking booking)
	 * { return dao.putBooking(id, booking); }
	 * 
	 * @RequestMapping(value = "/bookingList", method = RequestMethod.DELETE) public
	 * void deleteBookingList() { dao.deleteBookingList(); }
	 * 
	 * @RequestMapping(value = "/bookingList/{id}", method = RequestMethod.DELETE)
	 * public Object deleteBooking(@PathVariable int id) { return
	 * dao.deleteBooking(id); }
	 */

}
