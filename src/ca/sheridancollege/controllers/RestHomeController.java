package ca.sheridancollege.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.DAO.BookingDAO;
import ca.sheridancollege.DAO.CourtDAO;
//import ca.sheridancollege.DAO.DAO;
import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Court;

@RestController // specify that this class is a controller
@RequestMapping("/api")
public class RestHomeController {
	//private DAO dao = new DAO();
	CourtDAO courtDAO = new CourtDAO();
	BookingDAO bookingDAO = new BookingDAO();

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
	

	// new Booking(id, bookingDate, bookingType, status, startDateTime,
	// endDateTime));
	@RequestMapping(value = "/bookingList/{customerName}/{bookingType}/{status}/{startDateTime}/{endDateTime}/{courtId}", method = RequestMethod.GET)
	public String postBookingListItem(@PathVariable String customerName,
			@PathVariable String bookingType, @PathVariable String status, @PathVariable String startDateTime,
			@PathVariable String endDateTime, @PathVariable int courtId) {
		
		//DateTimeFormatter FMT = new DateTimeFormatterBuilder().appendPattern("MM-dd-yyyy-HH-mm")
			//	.parseDefaulting(ChronoField.NANO_OF_DAY, 0).toFormatter().withZone(ZoneId.of("America/Toronto"));
		
		LocalDateTime bookingDate = LocalDateTime.now();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm");
		LocalDateTime startDateTimeLocal = LocalDateTime.parse(startDateTime, formatter);
		LocalDateTime endDateTimeLocal = LocalDateTime.parse(endDateTime, formatter);

		
		Booking booking = new Booking(customerName, bookingDate, bookingType, status, startDateTimeLocal, endDateTimeLocal);
		
		Court court = courtDAO.getCourt(courtId);
		court.getBookings().add(booking); //add the booking to the particular court
		
		courtDAO.saveCourt(court);
		
		return "Booking Saved";
	}

	/*
	// @RequestMapping(value="/studentList", method=RequestMethod.PUT,
	// headers={"Contenttype=application/json"})
	@RequestMapping(value = "/bookingList", method = RequestMethod.PUT)
	public void putBookingtList(@RequestBody List<Booking> bookingList) {
		dao.putBookingList(bookingList);
	}

	@RequestMapping(value = "/bookingList/{id}", method = RequestMethod.PUT)
	public String putBooking(@PathVariable int id, @RequestBody Booking booking) {
		return dao.putBooking(id, booking);
	}

	@RequestMapping(value = "/bookingList", method = RequestMethod.DELETE)
	public void deleteBookingList() {
		dao.deleteBookingList();
	}

	@RequestMapping(value = "/bookingList/{id}", method = RequestMethod.DELETE)
	public Object deleteBooking(@PathVariable int id) {
		return dao.deleteBooking(id);
	}
	*/

}
