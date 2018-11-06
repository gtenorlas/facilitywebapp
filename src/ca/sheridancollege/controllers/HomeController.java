package ca.sheridancollege.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.DAO.App;
import ca.sheridancollege.DAO.BookingDAO;
import ca.sheridancollege.DAO.CourtDAO;
//import ca.sheridancollege.DAO.DAO;
import ca.sheridancollege.DAO.FacilityDAO;
import ca.sheridancollege.DAO.OpenStreetMapUtils;
import ca.sheridancollege.DAO.UserDAO;
import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Court;
import ca.sheridancollege.beans.Facility;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserRole;

@Controller // specify that this class is a controller
@RequestMapping("/")
public class HomeController {

	// DAO dao = new DAO();
	FacilityDAO facilityDao = new FacilityDAO();
	CourtDAO courtDAO = new CourtDAO();
	BookingDAO bookingDAO = new BookingDAO();
	UserDAO userDAO = new UserDAO();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		Facility facility = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName(); // grab the user currently authenticated

			facility = facilityDao.getFacility(username);
			model.addAttribute("facility", facility);
			System.out.println("load facility page");
		}
		return "/index";
	}

	@RequestMapping(value = "/contactUs", method = RequestMethod.GET)
	public String contactUs(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		Facility facility = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName(); // grab the user currently authenticated

			facility = facilityDao.getFacility(username);
			model.addAttribute("facility", facility);
			System.out.println("load facility page");
		}
		
		System.out.println("in contact us");
		return "contactUs";
	}

	@RequestMapping(value = "/pdfs/{file}", method = RequestMethod.GET)
	public void getLogFile(Model model, @PathVariable String file, HttpSession session, HttpServletResponse response)
			throws Exception {
		try {

			String fileName = "Report.pdf";
			// String filePathToBeServed =
			// "D:\\Semester6Java\\A0Project\\WebContent\\pdfs\\";
			String filePathToBeServed = "/images/";
			File fileToDownload = new File(filePathToBeServed + fileName);

			InputStream inputStream = new FileInputStream(fileToDownload);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

	}

	/*
	 * CRUD can be operated only when user is logged in.
	 */
	@RequestMapping(value = "/courts", method = RequestMethod.GET)
	public String courts(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName(); // grab the user currently authenticated

			Facility facility = facilityDao.getFacility(username);
			model.addAttribute("facility", facility);
			System.out.println("load facility page");
		}

		return "courts";
	}

	/*
	 * Get all the bookings for a particular facility and courts
	 */
	@RequestMapping(value = "/bookings", method = RequestMethod.GET)
	public String bookings(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		Facility facility = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName(); // grab the user currently authenticated

			facility = facilityDao.getFacility(username);
			model.addAttribute("facility", facility);
			System.out.println("load facility page");
		}

		//List<Booking> bookings = new ArrayList<Booking>();

		ArrayList<Booking> bookings = new ArrayList<Booking>();
		for (Court eachCourt : facility.getCourts()) {
			for (Booking eachBooking : eachCourt.getBookings()) {
				bookings.add(eachBooking);
			}
		}

		model.addAttribute("bookings", bookings);
		
		// create the pdf before showing the courts
		App createPdf = new App(facility, username, bookings);
		try {
			System.out.println("facility court size: " + facility.getCourts().size());
			createPdf.main();
		} catch (Exception e) {
			System.out.println("Cannot create pdf");
		}

		return "bookings";
	}

	/*
	 * View selected court from courts Comment cannot be added
	 */
	@RequestMapping(value = "/courts/view/{id}", method = RequestMethod.GET)
	public String courtsView(Model model, @PathVariable int id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		Facility facility = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName(); // grab the user currently authenticated

			facility = facilityDao.getFacility(username);
			model.addAttribute("facility", facility);
			System.out.println("load facility page");
		}
		
		Court court = courtDAO.getCourt(id);
		model.addAttribute("court", court);

		return "viewCourt";
	}

	/*
	 * Edit selected booking from courts Comment cannot be added
	 */
	@RequestMapping(value = "/bookings/edit/{bookingId}/{courtNumber}", method = RequestMethod.GET)
	public String bookingsEdit(Model model, @PathVariable int bookingId, @PathVariable int courtNumber) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		Facility facility = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName(); // grab the user currently authenticated

			facility = facilityDao.getFacility(username);
			model.addAttribute("facility", facility);
			System.out.println("load facility page");
		}
		
		Booking booking = bookingDAO.getBookingByID(bookingId);
		model.addAttribute("courtNumber", courtNumber);
		model.addAttribute("booking", booking);
		return "updateBooking";
	}

	/*
	 * Edit selected court from courts Comment cannot be added
	 */
	@RequestMapping(value = "/courts/edit/{facilityId}/{courtNumber}", method = RequestMethod.GET)
	public String courtsEdit(Model model, @PathVariable int facilityId, @PathVariable int courtNumber) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		Facility facility = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName(); // grab the user currently authenticated

			facility = facilityDao.getFacility(username);
			model.addAttribute("facility", facility);
			System.out.println("load facility page");
		}
		
		Court court = courtDAO.getCourt(courtNumber);
		model.addAttribute("facilityId", facilityId);
		model.addAttribute("court", court);
		return "createCourt";
	}

	/*
	 * Delete selected court from courts
	 *
	 */
	@RequestMapping(value = "/courts/delete/{facilityId}/{courtNumber}", method = RequestMethod.GET)
	public String courtsDelete(Model model, @PathVariable int facilityId, @PathVariable int courtNumber) {
		System.out.println("courtsToDelete");

		System.out.println("beforebookingDatetime");
		LocalDateTime bookingDateTime = LocalDateTime.now();
		
		System.out.println("bookingDAO");
		boolean canDelete = bookingDAO.canDeleteCourt(courtNumber, bookingDateTime);
		System.out.println("endbookingDAO");
		
		// facility.getCourts().remove(court);
		//boolean check = courtDAO.deleteCourt(court);
		// courtDAO.setInactive(court);
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
		// HH:mm:ss");
		// LocalDateTime joiningDate = LocalDateTime.parse("2014-04-01 00:00:00",
		// formatter);
		// court.setEndDate(LocalDateTime.now());
		// court.setEndDate(joiningDate);
		
		//Facility facility = facilityDao.getFacility(facilityId);
		if (canDelete == true) {
			System.out.println("court can be deleted");
			Court court = courtDAO.getCourt(courtNumber);
			System.out.println("Court to delete name " + court.getCourtName());
			court.setEndDate(bookingDateTime);
			court.setAvailability("Inactive");
			courtDAO.saveCourt(court);
			model.addAttribute("courtDeleted", true);
		}else {
			model.addAttribute("courtDeleted", false);
		}
		Facility facility=facilityDao.getFacility(facilityId);
		model.addAttribute("facility", facility);

		System.out.println("load facility page");
		return "courts";
	}

	/*
	 * handle to login
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm(Model model) {
		return "loginForm";
	}

	@RequestMapping(value = "/createAccount", method = RequestMethod.GET)
	public String createAccount(Model model) {
		return "createAccount";
	}

	@RequestMapping(value = "/createFacility", method = RequestMethod.GET)
	public String createFacility(Model model) {
		Facility facility = new Facility();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName(); // grab the user currently authenticated
			facility.setUsername(username);
			// model.addAttribute("username",username);

		}

		model.addAttribute("facility", facility);
		System.out.println("load facility page");

		return "createFacility";
	}

	/*
	 * Retrieve all facilities and view them to facilities.jsp
	 */
	@RequestMapping(value = "/facilities", method = RequestMethod.GET)
	public String facilities(Model model) {
		
		List<Facility> facilities = facilityDao.getFacilities();
		model.addAttribute("facilities", facilities);
		System.out.println("load facility page");

		return "facilities";
	}

	/*
	 * Retrieve all facilities and view them to facilities.jsp based on keyword
	 * search
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String facilities(Model model, @RequestParam String keyword) {
		List<Facility> facilities = facilityDao.searchFacilities(keyword);
		model.addAttribute("facilities", facilities);
		System.out.println("load facility page");

		return "facilities";
	}

	/*
	 * Retrieve all facilities and view them to facilities.jsp based on keyword
	 * search
	 */
	@RequestMapping(value = "/searchBooking", method = RequestMethod.GET)
	public String searchBooking(Model model, @RequestParam String customerName, @RequestParam String status,
			@RequestParam String startDT) {

		Facility facility = null;
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName(); // grab the user currently authenticated

			facility = facilityDao.getFacility(username);

		}

		System.out.println("filter booking");
		System.out.println("startDT " + startDT);
		LocalDateTime localDateTime = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		if (!startDT.equals("")) {
			startDT += " 11:05";
			localDateTime = LocalDateTime.parse(startDT, formatter);

		}
		if (status.equals("")) {
			status = null;

		}
		if (customerName.equals("")) {
			customerName = null;
		}
		//List<Booking> bookings = bookingDAO.searchBookings(customerName, status, localDateTime);
		List<Booking> bookings = bookingDAO.searchBookings(customerName, status, localDateTime);
		
		// create the pdf before showing the courts
		App createPdf = new App(facility, username, (ArrayList)bookings);
		try {
			System.out.println("facility court size: " + facility.getCourts().size());
			createPdf.main();
		} catch (Exception e) {
			System.out.println("Cannot create pdf");
		}


		

		model.addAttribute("bookings", bookings);

		return "bookings";
	}

	@RequestMapping(value = "/createCourt/{facilityId}", method = RequestMethod.GET)
	public String createCourt(Model model, @PathVariable int facilityId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		Facility facility = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName(); // grab the user currently authenticated

			facility = facilityDao.getFacility(username);
			model.addAttribute("facility", facility);
			System.out.println("load facility page");
		}

		Court court = new Court();
		model.addAttribute("facilityId", facilityId);
		model.addAttribute("court", court);

		return "createCourt";
	}

	/*
	 * Method to handle when creating a new facility or updating a facility
	 */
	@RequestMapping(value = "/saveCourt", method = RequestMethod.POST)
	public String saveCourt(Model model, @ModelAttribute("court") Court court, @RequestParam int facilityId) {

		System.out.println("Trying to save Court : " + court.getCourtName());
		System.out.println("Trying to save facility id : " + facilityId);
		Facility facilityToSave = facilityDao.getFacility(facilityId);
		
		court.setCreationDate(LocalDateTime.now());
		court.setFacility(facilityToSave);
		courtDAO.saveCourt(court);
		
		//facilityToSave.getCourts().remove(court);
		//facilityToSave.getCourts().add(court);

		System.out.println("getcourt ");
		//facilityDao.saveFacility(facilityToSave);
		//System.out.println("save facility ");

		Facility facility = facilityDao.getFacility(facilityId);
		model.addAttribute("facility", facility);
		System.out.println("load facility page");
		return "courts";
	}

	/*
	 * Method to handle when creating a new facility or updating a facility
	 */
	@RequestMapping(value = "/saveBooking", method = RequestMethod.GET)
	public String saveBooking(Model model, @ModelAttribute("booking") Booking booking, @RequestParam int courtNumber,
			@RequestParam String startDT, @RequestParam String endDT) {

		LocalDateTime localstartdatetime = null;
		LocalDateTime localenddatetime = null;
		System.out.println("update date: ");
		Court courtToSave = courtDAO.getCourt(courtNumber);
		if (startDT != null) {
			localstartdatetime = LocalDateTime.parse(startDT);

		}
		if (endDT != null) {
			localenddatetime = LocalDateTime.parse(endDT);
		}
		booking.setEndDateTime(localenddatetime);
		booking.setStartDateTime(localstartdatetime);
		booking.setBookingDate(LocalDateTime.now());

		// calculate the duration first
		// long diffInHours =
		// ChronoUnit.HOURS.between(booking.getEndDateTime(),booking.getStartDateTime());
		long diffInMinutes = ChronoUnit.MINUTES.between(booking.getEndDateTime(), booking.getStartDateTime());

		// String hourDuration=(diffInHours + "."+diffInMinutes);
		System.out.println("minutes : " + diffInMinutes);
		booking.setDuration(Math.abs(diffInMinutes) / 60.0);

		booking.setCourt(courtToSave); // added 10/25/2018

		courtToSave.getBookings().remove(booking);

		// ADJUST THE PAYMENT VALUE

		courtToSave.getBookings().add(booking);
		courtDAO.saveCourt(courtToSave);
		System.out.println("Trying to save Court : " + courtToSave.getCourtName());
		this.bookings(model);

//		Booking bookingToSave = bookingDAO.getBookingByID(1);
//		bookingToSave.getAllBookings().remove(booking);
//		bookingToSave.getAllBookings().add(booking);
//		bookingDAO.saveBooking(bookingToSave);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Facility facility = facilityDao.getFacility(authentication.getName());
		model.addAttribute("facility", facility);
		// court.setCreationDate(LocalDateTime.now());

		// bookingToSave.getCourts().remove(court);
		// bookingToSave.getCourts().add(court);

		// System.out.println("getcourt ");
		// bookingDAO.saveBooking(bookingToSave);
		// bookings(null);
		// System.out.println("save facility ");

		// List<Booking> allbooking = bookingDAO.getAllBookings();
		// model.addAttribute("bookingToSave",bookingToSave);
		// System.out.println("load facility page");
		return "bookings";
	}

	/*
	 * Method to handle when creating a new facility or updating a facility
	 */
	@RequestMapping(value = "/saveFacility", method = RequestMethod.POST)
	public String saveFacility(Model model, @ModelAttribute("facility") @Valid Facility facilityToSave,
			BindingResult result) {
		Map<String, Double> coords;
		
		if (result.hasErrors()) {
			return "createFacility";
		}

		System.out.println("Trying to save facility : " + facilityToSave.getFacilityName());

		String address = "";
		// address = "The White House Washington DC";
		address += facilityToSave.getLine_1().trim() + ", " + facilityToSave.getLine_2().trim() + ", " + facilityToSave.getLine_3().trim() + ", "
		+ facilityToSave.getCity().trim() + ", " + facilityToSave.getProvince().trim();
		address = address.replaceAll(",", "%2C");
		address = address.replaceAll(" ", "+");

		System.out.println("address ->" + address);

		coords = OpenStreetMapUtils.getInstance().getCoordinates(address);
		if (coords.get("lat") == null) {
			address += facilityToSave.getFacilityName()+ ", " + facilityToSave.getCity().trim() + ", " + facilityToSave.getProvince().trim();
			address = address.replaceAll(",", "%2C");
			address = address.replaceAll(" ", "+");
			coords = OpenStreetMapUtils.getInstance().getCoordinates(address);
		}
		

		if (coords.get("lat") != null && coords.get("lon") != null) {

			System.out.println("latitude :" + coords.get("lat"));
			System.out.println("longitude:" + coords.get("lon"));

			facilityToSave.setCreationDate(LocalDateTime.now());
			facilityToSave.setLat(coords.get("lat")+"");
			facilityToSave.setLng(coords.get("lon")+"");

			facilityDao.saveFacility(facilityToSave); // create new or update

			System.out.println("im done resaving the user");

			Facility facility = facilityDao.getFacilityJustRegistered(facilityToSave.getUsername());

			model.addAttribute("facility", facility);
			System.out.println("load facility page");

			// check if user is logged on -- just added
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				System.out.println("logged in after saveFAcility");
				return "courts";
			} else {
				System.out.println("NOt logged in after saveFAcility");
				model.addAttribute("accountCreated", true);
				return "loginForm";
			}
		}else {
			//result.addError(new ObjectError("line_1", "Invalid"));
			result.rejectValue("line_1", "error.facility", "Please validate the address.");
			result.rejectValue("city", "error.facility", "Please validate the city.");
			result.rejectValue("province", "error.facility", "Please validate the province.");
			return "createFacility";
		}

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model, @RequestParam String username, @RequestParam String password,
			HttpServletRequest request, HttpServletResponse response) {
		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
		User user = new User(username, encryptedPassword, true);

		UserRole userRole = new UserRole(user, "ROLE_FACILITY_ADMIN");
		user.getUserRole().add(userRole);

		// disable the user first
		// user.setEnabled(false);

		// Generate random 36-character string token for confirmation link
		user.setConfirmationToken(UUID.randomUUID().toString());

		userDAO.createUser(user);
		// dao.createUserRole(userRole);

		System.out.println("New user created " + user.getUsername());

//		//logout the user
//	    Authentication authentic = SecurityContextHolder.getContext().getAuthentication();
//	    if (authentic != null){    
//	        new SecurityContextLogoutHandler().logout(request, response, authentic);
//	    }
//		
//		//load the user to be logged in right after the registration
//		
//		
//		UserDetails userDetails = new MyUserDetailsService().loadUserByUsername(username);
//		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
//		encryptedPassword, userDetails.getAuthorities());
//		SecurityContextHolder.getContext().setAuthentication(auth);
//		
//		
//		System.out.println("New user auto logged in " + user.getUsername());

		model.addAttribute("accountCreated", true);
		model.addAttribute("username", username);

		Facility facility = new Facility();
		facility.setUsername(username); // set facility username to the user
		model.addAttribute("facility", facility);

		return "/createFacility";
	}

	/*
	 * handle to logout a user
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/"; // redirect to home
	}
}
