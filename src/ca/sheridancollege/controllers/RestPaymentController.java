package ca.sheridancollege.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import ca.sheridancollege.DAO.PaymentDAO;

@RestController // specify that this class is a restful controller
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api/payment")
public class RestPaymentController {
	PaymentDAO paymentDAO = new PaymentDAO();

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

}
