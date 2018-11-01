package ca.sheridancollege.controllers;

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
	private int paymentId;
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY)
    	@JoinColumn(name = "bookingId")
	private Booking booking;
	private double courtCharge; //hourly rate of the court
	private double adminFee; //top up cost for using the app
	private double subTotal; //courtCharge + adminFee
	private double taxPercentage; //tax in percentage
	private double taxAmount; //tax calculated based on subtotal
	private double totalAmount; //subTotal + taxAmount
	private LocalDateTime paymentDateTime;
	private String confirmationNumber;
	private String paymentMethod;
	private String status;
	 */
	@RequestMapping(value = "/{bookingId}/{courtCharge}/{adminFee}/{subTotal}/{taxPercentage}/{taxAmount}/{totalAmount}/{confirmationNumber}/{paymentMethod}/{status}", method = {
			RequestMethod.OPTIONS, RequestMethod.POST })
	public Object postPaymentItem(@PathVariable int bookingId,@PathVariable double courtCharge, @PathVariable double adminFee, @PathVariable double subTotal, 
			@PathVariable double taxPercentage, @PathVariable double taxAmount, @PathVariable double totalAmount, @PathVariable String confirmationNumber, 
			@PathVariable String paymentMethod, @PathVariable String status) {
		LocalDateTime paymentDateTime = LocalDateTime.now();
		Payment payment= new Payment();
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
		if (id==0) {
			return "invalid";
		}
			
		return id;
		
	}

}
