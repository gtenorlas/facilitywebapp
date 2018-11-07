package ca.sheridancollege.controllers;

import java.awt.Color;
import java.time.LocalDateTime;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import ca.sheridancollege.DAO.BookingDAO;
import ca.sheridancollege.DAO.PaymentDAO;
import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Email;
import ca.sheridancollege.beans.Payment;

@RestController // specify that this class is a restful controller
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api/payment")
public class RestPaymentController {
	PaymentDAO paymentDAO = new PaymentDAO();
	BookingDAO bookingDAO = new BookingDAO();

	public RestPaymentController() {
		CorsRegistry registry = new CorsRegistry();
		registry.addMapping("/api/payment").allowedOrigins("*", "http://localhost:8080")
				.allowedMethods("POST", "GET", "PUT", "DELETE").allowedHeaders("Content-Type")
				.exposedHeaders("header-1", "header-2").allowCredentials(false).maxAge(6000);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET) // routing to home))
	public Object home(Model model) {
		return paymentDAO.getAllPayments();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // routing to home
	public Object facilityItem(Model model, @PathVariable int id) {
		return paymentDAO.getPaymentByID(id);
	}

	/*
	 *
	 * private int paymentId;
	 * 
	 * @JsonBackReference
	 * 
	 * @OneToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "bookingId") private Booking booking; private double
	 * courtCharge; //hourly rate of the court private double adminFee; //top up
	 * cost for using the app private double subTotal; //courtCharge + adminFee
	 * private double taxPercentage; //tax in percentage private double taxAmount;
	 * //tax calculated based on subtotal private double totalAmount; //subTotal +
	 * taxAmount private LocalDateTime paymentDateTime; private String
	 * confirmationNumber; private String paymentMethod; private String status;
	 */
	@RequestMapping(value = "/{bookingId}/{courtCharge}/{adminFee}/{subTotal}/{taxPercentage}/{taxAmount}/{totalAmount}/{confirmationNumber}/{paymentMethod}/{status}", method = {
			RequestMethod.OPTIONS, RequestMethod.POST })
	public Object postPaymentItem(@PathVariable int bookingId, @PathVariable double courtCharge,
			@PathVariable double adminFee, @PathVariable double subTotal, @PathVariable double taxPercentage,
			@PathVariable double taxAmount, @PathVariable double totalAmount, @PathVariable String confirmationNumber,
			@PathVariable String paymentMethod, @PathVariable String status) {
		LocalDateTime paymentDateTime = LocalDateTime.now();
		Payment payment = new Payment();
		Booking booking = bookingDAO.getBookingByID(bookingId);
		payment.setBooking(booking);
		payment.setCourtCharge(courtCharge);
		payment.setAdminFee(adminFee);
		payment.setSubTotal(subTotal);
		payment.setTaxPercentage(taxPercentage);
		payment.setTaxAmount(taxAmount);
		payment.setTotalAmount(totalAmount);
		payment.setPaymentDateTime(paymentDateTime);
		payment.setConfirmationNumber(confirmationNumber);
		payment.setPaymentMethod(paymentMethod);
		payment.setStatus(status);

		int id = paymentDAO.savePayment(payment);
		if (id == 0) {
			return "invalid";
		}

		
		Email newEmail = new Email(booking.getCustomerEmail(), "Your New Booking",
				"<font color=black>Congratulation in your recent booking with Book2Ball! <br/></font>" +

						"<font color=black><h3>Facility Details:</h3></font>"

						+ "<font color=black>" + booking.getFacilityName() + "<br/>" 
						+ booking.getFaciltyAddress() + "<br/>"
						+ "<b>Court Name:</b> " + booking.getCourtName() + "<br/>" 
						+ "<b>Start Date Time:</b> "+ Booking.formatDate(booking.getStartDateTime()) + "<br/>"
						+ "<b>End Date Time:</b> "
						+ Booking.formatDate(booking.getEndDateTime()) + "<br/>"
						+ "<b>Booking Status:</b> " + booking.getStatus()
						+ "</font><br/>"

						+ "<font color=black><h3>Payment Details:</h3>"
						+ "<b>Payment Date:</b> " + Booking.formatDate(payment.getPaymentDateTime())+"<br/>"
						+ "<font color=black><b>Court Charge:</b> " + Payment.formatToCurrency(payment.getCourtCharge()) + "<br/>"
						+ "<b>Duration:</b> " + booking.getDuration() + "<br/>"
						+ "<b>Sub Total:</b> "	+ Payment.formatToCurrency(payment.getSubTotal()) + "<br/>"
						+ "<b>Tax Percentage:</b> "	+ payment.getTaxPercentage() + "%<br/>"
						+ "<b>Tax Amount:</b> "	+ Payment.formatToCurrency(payment.getTaxAmount()) + "<br/><hr/>" 
						+ "<b>Total Amount:</b> " + Payment.formatToCurrency(payment.getTotalAmount()) + "<br/></font>"

						+ "<br/><br/>" +

						"<font color=black>If you have not authorized this booking, please contact Book2ball with the information in this e-mail.<br/>"
						+ "THANK YOU!<br/>" + "<b>MAGS.WEBSITE</b></font>");
		newEmail.send();
		

		/*
		Email newEmail = new Email(booking.getCustomerEmail(), "Your Booking",
				"Congratulation in your recent booking with Book2Ball! \r\n" + "\r\n\n" +

						"<h3>Facility Details:</h3> \n"

						+ booking.getFacilityName() + "\n" + booking.getFaciltyAddress() + "\n" + "Court Name: "
						+ booking.getCourtName() + "\n\n" + "<b>Start Date Time:</b> "
						+ Booking.formatDate(booking.getStartDateTime()) + "\n" + "End Date Time: "
						+ Booking.formatDate(booking.getEndDateTime()) + "\n" + "Booking Status: " + booking.getStatus()
						+ "\n\n"

						+ "<h3>Payment Details</h3>: \n" + "Payment Date: " + Booking.formatDate(payment.getPaymentDateTime())
						+ "\n" + "Court Charge: " + Payment.formatToCurrency(payment.getCourtCharge()) + "\n"
						+ "Duration: " + booking.getDuration() + "\n" + "Sub Total: "
						+ Payment.formatToCurrency(payment.getSubTotal()) + "\n" + "Tax Percentage: "
						+ payment.getTaxPercentage() + "%\n" + "Tax Amount: "
						+ Payment.formatToCurrency(payment.getTaxAmount()) + "\n" + "Total Amount: "
						+ Payment.formatToCurrency(payment.getTotalAmount()) + "\n"

						+ "\r\n\n\n" +

						"If you have not authorized this booking, please contact Book2ball with the information in this e-mail.\r\n"
						+ "THANK YOU!\r\n" + "\r\n" + "MAGS.WEBSITE\r\n");
		newEmail.send();
		*/

		return id;

	}

}
