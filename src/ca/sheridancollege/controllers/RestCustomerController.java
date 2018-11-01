package ca.sheridancollege.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import ca.sheridancollege.DAO.CustomerDAO;
import ca.sheridancollege.beans.Customer;
import ca.sheridancollege.beans.Email;

@RestController // specify that this class is a restful controller
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api/customer")
public class RestCustomerController {
	CustomerDAO customerDAO = new CustomerDAO();

	public RestCustomerController() {
		CorsRegistry registry = new CorsRegistry();
		registry.addMapping("/api/customer").allowedOrigins("*", "http://localhost:8080")
				.allowedMethods("POST", "GET", "PUT", "DELETE").allowedHeaders("Content-Type")
				.exposedHeaders("header-1", "header-2").allowCredentials(false).maxAge(6000);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET) // routing to home
	public String home(Model model) {
		return "home";

	}

	@RequestMapping(value = "/{username}/{password}/{originate}", method = RequestMethod.GET)
	public Object getCustomer(@PathVariable String username, @PathVariable String password,
			@PathVariable String originate) {
		if (originate.equals("standard")) {
			return customerDAO.getCustomer(username, password);
		} else {
			return customerDAO.getCustomer(username);
		}
	}
	
	@RequestMapping(value = "/token/{email}/", method = RequestMethod.POST)
	public Object getToken(@PathVariable String email) {
		Customer customer=(Customer)customerDAO.getCustomerByEmail(email.trim());
		if (customer ==null ) {
			return "invalid";
		}else {
			String token = Customer.generateToken();
			customer.setConfirmationToken(token);
			customerDAO.updateCustomer(customer);
			Email newEmail = new Email(email,
                    "Token", "You've requested a password reset for your Book2ball account login. Please enter the token below in the Book2ball app, and you'll be able to create a new password. \r\n" + 
                    		"\r\n" + 
                    		"Token: " + token + 
                    		"\r\n\n\n" + 
      
                    		"If you have not authorized this change, please contact Book2ball with the information in this e-mail.\r\n" + 
                    		"THANK YOU!\r\n" + 
                    		"\r\n" + 
                    		"MAGS.WEBSITE\r\n");
            newEmail.send();
			return "success";
		}
	}
	
	@RequestMapping(value = "/token/validate/{email}/{token}/", method = RequestMethod.POST)
	public Object validateToken(@PathVariable String email, @PathVariable String token) {
		Customer customer=(Customer)customerDAO.getCustomerByEmail(email.trim());
		if (customer ==null ) {
			return "invalid";
		}else {
			if(customer.getConfirmationToken().equals(token.trim())) {
				return "success";
			}else {
			return "invalid";
			}
		}
	}
	
	@RequestMapping(value = "/reset/{email}/{newPassword}/", method = RequestMethod.POST)
	public Object resetPassword(@PathVariable String email, @PathVariable String newPassword) {
		Customer customer=(Customer)customerDAO.getCustomerByEmail(email.trim());
		if (customer ==null ) {
			return "invalid";
		}else {
			customer.setPassword(Customer.hashPassword(newPassword));
			customerDAO.updateCustomer(customer);
			return "success";
		}
	}


	// new Booking(id, bookingDate, bookingType, status, startDateTime,
	// endDateTime));
	@RequestMapping(value = "/{username}/{password}/{firstName}/{lastName}/{email}/{contactNumber}/{startDate}/{endDate}/{status}/{originate}", method = {
			RequestMethod.OPTIONS, RequestMethod.POST })
	public Object postCustomerItem(@PathVariable String username, @PathVariable String password,
			@PathVariable String firstName, @PathVariable String lastName, @PathVariable String email,
			@PathVariable String contactNumber, @PathVariable String startDate, @PathVariable String endDate,
			@PathVariable String status, @PathVariable String originate) {

		// String encryptedPassword = new BCryptPasswordEncoder().encode(password);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm");
		//LocalDateTime startDateTimeLocal = LocalDateTime.parse(startDate, formatter);
		LocalDateTime startDateTimeLocal = LocalDateTime.now();
		//LocalDateTime endDateTimeLocal = null;
		//if (endDate != null && !endDate.equals("null")) {
		//	endDateTimeLocal = LocalDateTime.parse(endDate, formatter);
		//}

		Customer customer = new Customer(username, password, firstName, lastName, email, contactNumber,
				startDateTimeLocal, null, status, originate, null);
		customer.setPassword(Customer.hashPassword(password));
		if (!customerDAO.isDuplicate(username)) {
			int id = customerDAO.saveCustomer(customer);
			System.out.println("Email saved is: " + customer.getEmail());
			if (id != 0) {
				return id;
			}else {
				return "Customer did not save due to internal error";
			}
		} else {
			return "Customer already exists.";
		}
	}

}
