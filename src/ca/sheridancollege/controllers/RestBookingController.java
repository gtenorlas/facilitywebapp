package ca.sheridancollege.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import ca.sheridancollege.beans.Email;
import ca.sheridancollege.beans.Facility;
import ca.sheridancollege.beans.Payment;

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

	@RequestMapping(value = "/email/{email}/", method = RequestMethod.GET)
	public Object getBookings(@PathVariable String email) {
		System.out.println("Trying to get all booking by email");
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
		booking.setStatus("Active");

		// bookingDAO.saveBooking(booking);

		if (bookingDAO.bookingValidation(courtId, startDateTimeLocal, endDateTimeLocal)) {
			// Court court = courtDAO.getCourt(courtId);
			Court court = courtDAO.getCourt(courtId);
			Payment payment = new Payment();
			if (court != null) {
				if (court.getAvailability().toLowerCase().equals("active")) {
					booking.setCourt(court);
					booking.setCourtName(court.getCourtName());
					Facility facility = court.getFacility();
					String address = facility.getLine_1() + "\n";
					if (facility.getLine_2() != null) {
						if (!facility.getLine_2().trim().isEmpty()) {
							address += facility.getLine_2() + "\n";
						}

					}
					if (facility.getLine_3() != null) {
						if (!facility.getLine_3().trim().isEmpty()) {
							address += facility.getLine_3() + "\n";
						}

					}
					address += facility.getCity() + ", ";
					address += facility.getProvince() + "\n";
					if (facility.getPostalCode() != null) {
						if (!facility.getPostalCode().trim().isEmpty()) {
							address += facility.getPostalCode() + "\n";
						}

					}
					address += facility.getCountry();

					booking.setFaciltyAddress(address);

					payment.setBooking(booking);
					payment.setCourtCharge(court.getPrice());
					payment.setAdminFee(0);
					payment.setSubTotal(court.getPrice() * booking.getDuration());
					payment.setTaxPercentage(13.00);
					payment.setTaxAmount(payment.getSubTotal() * (payment.getTaxPercentage() / 100));
					payment.setTotalAmount(payment.getSubTotal() + payment.getTaxAmount());
					payment.setPaymentDateTime(bookingDateTime);
					payment.setConfirmationNumber(null);
					payment.setPaymentMethod("payPal");
					payment.setStatus("Paid");
					booking.setPayment(payment);

					int id = bookingDAO.saveBookingForAPI(booking);
					if (id != 0) {
						Email newEmail = new Email(booking.getCustomerEmail(), "Your New Booking",
								"<font color=black>Congratulation in your recent booking with Book2Ball! <br/></font>" +

										"<font color=black><h3>Facility Details:</h3></font>"

										+ "<font color=black>" + booking.getFacilityName() + "<br/>"
										+ booking.getFaciltyAddress() + "<br/>" + "<b>Court Name:</b> "
										+ booking.getCourtName() + "<br/>" + "<b>Start Date Time:</b> "
										+ Booking.formatDate(booking.getStartDateTime()) + "<br/>"
										+ "<b>End Date Time:</b> " + Booking.formatDate(booking.getEndDateTime())
										+ "<br/>" + "<b>Booking Status:</b> " + booking.getStatus() + "</font><br/>"

										+ "<font color=black><h3>Payment Details:</h3>" + "<b>Payment Date:</b> "
										+ Booking.formatDate(payment.getPaymentDateTime()) + "<br/>"
										+ "<font color=black><b>Court Charge:</b> "
										+ Payment.formatToCurrency(payment.getCourtCharge()) + "<br/>"
										+ "<b>Duration:</b> " + booking.getDuration() + "<br/>" + "<b>Sub Total:</b> "
										+ Payment.formatToCurrency(payment.getSubTotal()) + "<br/>"
										+ "<b>Tax Percentage:</b> " + payment.getTaxPercentage() + "%<br/>"
										+ "<b>Tax Amount:</b> " + Payment.formatToCurrency(payment.getTaxAmount())
										+ "<br/><hr/>" + "<b>Total Amount:</b> "
										+ Payment.formatToCurrency(payment.getTotalAmount()) + "<br/></font>"

										+ "<br/><br/>" +

										"<font color=black>If you have not authorized this booking, please contact Book2ball with the information in this e-mail.<br/>"
										+ "THANK YOU!<br/>" + "<b>MAGS.WEBSITE</b></font>");
						newEmail.send();

						return id;
					} else {
						return "Internal error while booking save";
					}

				} else {
					return "Court not available";
				}
			} else {
				return "Court not available";
			}
		} else {
			return "Court not available";
		}

	}

}
