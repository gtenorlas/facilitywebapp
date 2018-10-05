package ca.sheridancollege.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import ca.sheridancollege.DAO.UserDAO;
import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Court;
import ca.sheridancollege.beans.Facility;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserRole;


@Controller //specify that this class is a controller
@RequestMapping("/")
public class HomeController {
	
	//DAO dao = new DAO();
	FacilityDAO facilityDao = new FacilityDAO();
	CourtDAO courtDAO = new CourtDAO();
	BookingDAO bookingDAO = new BookingDAO();
	UserDAO userDAO = new UserDAO();
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home (Model model) {
		return "/index";
	}
	
	@RequestMapping(value="/contactUs", method = RequestMethod.GET)
	public String contactUs (Model model) {
		System.out.println("in contact us");
		return "contactUs";
	}
		
	@RequestMapping(value = "/pdfs/{file}", method = RequestMethod.GET)
    public void getLogFile(Model model, @PathVariable String file, HttpSession session,HttpServletResponse response) throws Exception {
        try {
        	
        	

        	
        
        	
            String fileName="Report.pdf";
            //String filePathToBeServed = "D:\\Semester6Java\\A0Project\\WebContent\\pdfs\\";
            String filePathToBeServed = "/images/";
            File fileToDownload = new File(filePathToBeServed+fileName);

            InputStream inputStream = new FileInputStream(fileToDownload);
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName); 
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }

    }
		
	/*
	 * CRUD can be operated only when user is logged in.
	 */
	@RequestMapping(value="/courts", method=RequestMethod.GET)  
	public String courts(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String username = authentication.getName(); //grab the user currently authenticated

			Facility facility=facilityDao.getFacility(username);
			model.addAttribute("facility",facility);
			System.out.println("load facility page");
		}
		
		return "courts";
	}
	
	/*
	 * Get all the bookings for a particular facility and courts
	 */
	@RequestMapping(value="/bookings", method=RequestMethod.GET)  
	public String bookings(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username=null;
		Facility facility=null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    username = authentication.getName(); //grab the user currently authenticated

			 facility=facilityDao.getFacility(username);
			model.addAttribute("facility",facility);
			System.out.println("load facility page");
		}
		
		//create the pdf before showing the courts
		App createPdf=new App(facility, username);
		try {
			System.out.println("facility court size: "+facility.getCourts().size());
			createPdf.main();
		}catch(Exception e) {
			System.out.println("Cannot create pdf");
		}
    	
    	
		return "bookings";
	}
	
	/*
	 * View selected court from courts
	 * Comment cannot be added
	 */
	@RequestMapping(value="/courts/view/{id}", method=RequestMethod.GET)  
	public String courtsView(Model model, @PathVariable int id) {
		Court court=courtDAO.getCourt(id);
		model.addAttribute("court",court);
		
		return "viewCourt";	
	}
	
	/*
	 * Edit selected booking from courts 
	 * Comment cannot be added
	 */
	@RequestMapping(value="/bookings/edit/{bookingId}/{courtNumber}", method=RequestMethod.GET)  
	public String bookingsEdit(Model model, @PathVariable int bookingId,@PathVariable int courtNumber ) {
		Booking booking=bookingDAO.getBookingByID(bookingId);
		model.addAttribute("courtNumber", courtNumber);
		model.addAttribute("booking",booking);
		return "updateBooking";	
	}
	
	/*
	 * Edit selected court from courts 
	 * Comment cannot be added
	 */
	@RequestMapping(value="/courts/edit/{facilityId}/{courtNumber}", method=RequestMethod.GET)  
	public String courtsEdit(Model model, @PathVariable int facilityId, @PathVariable int courtNumber) {
		Court court=courtDAO.getCourt(courtNumber);
		model.addAttribute("facilityId", facilityId);
		model.addAttribute("court",court);
		return "createCourt";	
	}
	
	/*
	 * Delete selected court from courts
	 *
	 */
	@RequestMapping(value="/courts/delete/{facilityId}/{courtNumber}", method=RequestMethod.GET)  
	public String courtsDelete(Model model, @PathVariable int facilityId, @PathVariable int courtNumber) {
		
		Court court=courtDAO.getCourt(courtNumber);
		//facility.getCourts().remove(court);
		boolean check = courtDAO.deleteCourt(court);
		//courtDAO.setInactive(court);
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//LocalDateTime joiningDate = LocalDateTime.parse("2014-04-01 00:00:00", formatter);
		//court.setEndDate(LocalDateTime.now());
		//court.setEndDate(joiningDate);
		System.out.println(check);
		Facility facility=facilityDao.getFacility(facilityId);
		if (check == true) {
			courtDAO.getCourt(courtNumber);
			facility.getCourts().remove(court);
			model.addAttribute("courtDeleted",true);
		}
		//Facility facility=facilityDao.getFacility(facilityId);
		//facility.getCourts().remove(court);
		facility.getCourts();
		facilityDao.saveFacility(facility);
		model.addAttribute("courtDeleted",false);
		model.addAttribute("facility",facility);
		
		System.out.println("load facility page");
		return "courts";
	}
	
	/*
	 * handle to login
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)  
	public String loginForm(Model model) {
		return "loginForm";	
	}
	
	@RequestMapping(value="/createAccount", method=RequestMethod.GET)
	public String createAccount(Model model) {
	return "createAccount";
	}
	
	@RequestMapping(value="/createFacility", method=RequestMethod.GET)
	public String createFacility(Model model) {
		Facility facility=new Facility();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String username = authentication.getName(); //grab the user currently authenticated
		    facility.setUsername(username);
			//model.addAttribute("username",username);
	
		}
	
	
		
		model.addAttribute("facility",facility);
		System.out.println("load facility page");
		
		return "createFacility";
	}
	
	/*
	 * Retrieve all facilities and view them to facilities.jsp
	 */
	@RequestMapping(value="/facilities", method=RequestMethod.GET)  
	public String facilities(Model model) {
			List<Facility> facilities=facilityDao.getFacilities();
			model.addAttribute("facilities",facilities);
			System.out.println("load facility page");
		
		return "facilities";
	}
	
	/*
	 * Retrieve all facilities and view them to facilities.jsp based on keyword search
	 */
	@RequestMapping(value="/search", method=RequestMethod.GET)  
	public String facilities(Model model, @RequestParam String keyword) {
			List<Facility> facilities=facilityDao.searchFacilities(keyword);
			model.addAttribute("facilities",facilities);
			System.out.println("load facility page");
			
		
		return "facilities";
	}
	
	/*
	 * Retrieve all facilities and view them to facilities.jsp based on keyword search
	 */
	@RequestMapping(value="/searchBooking", method=RequestMethod.GET)  
	public String searchBooking(Model model, @RequestParam String customerName,  @RequestParam String status, @RequestParam String startDT) {
		
		System.out.println("filter booking");
		LocalDateTime localDateTime = null;
		if (!startDT.equals("")) {
			localDateTime = LocalDateTime.parse(startDT);

		}
		if (status.equals("")) {
			status = null;

		}
		List<Booking> bookings=bookingDAO.searchBookings(customerName,status,localDateTime);
		System.out.println("Bokings are these"+bookings);
		//booking
		
//Facility facility=new Facility();
//		for (Booking each : bookings) {
//			Court court=new Court();
//			court.setCourtNumber(each.get);
//		}

		
		model.addAttribute("bookings",bookings);
		

		
		return "bookings";
	}
	

	
	@RequestMapping(value="/createCourt/{facilityId}", method=RequestMethod.GET)
	public String createCourt(Model model, @PathVariable int facilityId) {
	
		    Court court=new Court();
		    model.addAttribute("facilityId", facilityId);
			model.addAttribute("court",court);
	
	return "createCourt";
	}
	
	/*
	 * Method to handle when creating a new facility or updating a facility
	 */
	@RequestMapping(value="/saveCourt", method=RequestMethod.GET) 
	public String saveCourt(Model model, @ModelAttribute("court") Court court, @RequestParam int facilityId) {


		
		System.out.println("Trying to save Court : " + court.getCourtName());
		System.out.println("Trying to save facility id : " + facilityId);
		Facility facilityToSave = facilityDao.getFacility(facilityId);
		court.setCreationDate(LocalDateTime.now());
		
		
		
		facilityToSave.getCourts().remove(court);
		facilityToSave.getCourts().add(court);
		
		System.out.println("getcourt ");
		facilityDao.saveFacility(facilityToSave);  
		System.out.println("save facility ");
		
		Facility facility=facilityDao.getFacility(facilityId);
		model.addAttribute("facility",facility);
		System.out.println("load facility page");
		return "courts";
	}
	
	
	/*
	 * Method to handle when creating a new facility or updating a facility
	 */
	@RequestMapping(value="/saveBooking", method=RequestMethod.GET) 
	public String saveBooking(Model model, @ModelAttribute("booking") Booking booking, @RequestParam int courtNumber , @RequestParam String startDT, @RequestParam String endDT) {

		LocalDateTime localstartdatetime=null;
		LocalDateTime localenddatetime=null;
		System.out.println("update date: " );
		Court courtToSave = courtDAO.getCourt(courtNumber);
		if (startDT!=null) {
			 localstartdatetime = LocalDateTime.parse(startDT);

		}
		 if (endDT!=null) {
			localenddatetime = LocalDateTime.parse(endDT);
		}
		booking.setEndDateTime(localenddatetime);
		booking.setStartDateTime(localstartdatetime);
		booking.setBookingDate(LocalDateTime.now());
		
		//calculate the duration first
		// long diffInHours = ChronoUnit.HOURS.between(booking.getEndDateTime(),booking.getStartDateTime());
		    long diffInMinutes = ChronoUnit.MINUTES.between(booking.getEndDateTime(),booking.getStartDateTime());
		    
		 //String hourDuration=(diffInHours + "."+diffInMinutes);
		   System.out.println("minutes : " + diffInMinutes);
		 booking.setDuration(Math.abs(diffInMinutes)/60.0);
		
		courtToSave.getBookings().remove(booking);
		courtToSave.getBookings().add(booking);
		courtDAO.saveCourt(courtToSave);
		System.out.println("Trying to save Court : " + courtToSave.getCourtName());
		this.bookings(model);
		
//		Booking bookingToSave = bookingDAO.getBookingByID(1);
//		bookingToSave.getAllBookings().remove(booking);
//		bookingToSave.getAllBookings().add(booking);
//		bookingDAO.saveBooking(bookingToSave);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Facility facility=facilityDao.getFacility(authentication.getName());
		model.addAttribute("facility",facility);
		//court.setCreationDate(LocalDateTime.now());
		
		
		
		//bookingToSave.getCourts().remove(court);
		//bookingToSave.getCourts().add(court);
		
		//System.out.println("getcourt ");
		//bookingDAO.saveBooking(bookingToSave);  
		//bookings(null);
		//System.out.println("save facility ");
		
		//List<Booking> allbooking = bookingDAO.getAllBookings();
		//model.addAttribute("bookingToSave",bookingToSave);
		//System.out.println("load facility page");
		return "bookings";
	}
	
	/*
	 * Method to handle when creating a new facility or updating a facility
	 */
	@RequestMapping(value="/saveFacility", method=RequestMethod.GET) 
	public String saveFacility(Model model, @ModelAttribute("facility") Facility facilityToSave) {


		
		System.out.println("Trying to save facility : " + facilityToSave.getFacilityName());
		
		facilityToSave.setCreationDate(LocalDateTime.now());
		
		facilityDao.saveFacility(facilityToSave);  //create new or update
		
		System.out.println("im done resaving the user");
		
		Facility facility=facilityDao.getFacilityJustRegistered(facilityToSave.getUsername());
		
		model.addAttribute("facility",facility);
		System.out.println("load facility page");
		
		//check if user  is logged on -- just added
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			System.out.println("logged in after saveFAcility");
			return "courts";
		}else {
			System.out.println("NOt logged in after saveFAcility");
			model.addAttribute("accountCreated", true);
			return "loginForm";	
		}
		
	
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(Model model, @RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {
		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
		User user = new User(username, encryptedPassword, true);
		
		
		UserRole userRole = new UserRole(user, "ROLE_USER");
		user.getUserRole().add(userRole);
		
		//disable  the user first
		//user.setEnabled(false);
		
		// Generate random 36-character string token for confirmation link
	    user.setConfirmationToken(UUID.randomUUID().toString());
		
		
		userDAO.createUser(user);
		//dao.createUserRole(userRole);
		
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
		model.addAttribute("username",username);
		
		Facility  facility=new Facility();
		facility.setUsername(username); //set facility username to the user
		model.addAttribute("facility",facility);
		
		return "/createFacility";
	}
	
	/*
	 * handle to logout a user
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/"; //redirect to home
	}
}
