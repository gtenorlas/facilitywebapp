package ca.sheridancollege.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import ca.sheridancollege.DAO.CustomerDAO;
import ca.sheridancollege.DAO.UserDAO;
import ca.sheridancollege.beans.Customer;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserRole;

@RestController // specify that this class is a restful controller
@CrossOrigin(origins = { "*"}, maxAge = 6000)
@RequestMapping("/api/customer")
public class RestCustomerController {
	CustomerDAO customerDAO = new CustomerDAO();
	
	public RestCustomerController(){
		CorsRegistry registry = new CorsRegistry();
		registry.addMapping("/api/customer")
		.allowedOrigins("*", "http://localhost:8080")
		.allowedMethods("POST", "GET", "PUT", "DELETE")
		.allowedHeaders("Content-Type")
		.exposedHeaders("header-1", "header-2")
		.allowCredentials(false)
		.maxAge(6000);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET) // routing to home
	public String home(Model model) {
		return "home";

	}



	@RequestMapping(value = "/{username}/{password}", method = RequestMethod.GET)
	public Object getBookingList(@PathVariable String username, @PathVariable String password) {
		return customerDAO.getCustomer(username, password);
	}
	

	// new Booking(id, bookingDate, bookingType, status, startDateTime,
	// endDateTime));
	@RequestMapping(value = "/{username}/{password}/{firstName}/{lastName}/{email}/{contactNumber}/{startDate}/{endDate}/{status}/{originate}", method = {RequestMethod.OPTIONS, RequestMethod.POST})
	public String postCustomerItem(@PathVariable String username, @PathVariable String password, @PathVariable String firstName, @PathVariable String lastName, @PathVariable String email, @PathVariable String contactNumber, @PathVariable String startDate,
			@PathVariable String endDate, @PathVariable String status, @PathVariable String originate) {
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
		User user = new User(username, encryptedPassword, true);
		
		
		UserRole userRole = new UserRole(user, "ROLE_CUSTOMER");
		user.getUserRole().add(userRole);
		
		
		// Generate random 36-character string token for confirmation link
	    user.setConfirmationToken(UUID.randomUUID().toString());
	    
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm");
		LocalDateTime startDateTimeLocal = LocalDateTime.parse(startDate, formatter);
		LocalDateTime endDateTimeLocal = null;
		if (endDate != null && !endDate.equals("null")) {
			 endDateTimeLocal = LocalDateTime.parse(endDate, formatter);
		}
		UserDAO userDAO = new UserDAO();
		userDAO.createUser(user);
		Customer customer = new Customer(username, user, firstName, lastName, email, contactNumber, startDateTimeLocal, endDateTimeLocal, status, originate);
		customerDAO.saveCustomer(customer);
		return "Customer is saved";
	}



}
