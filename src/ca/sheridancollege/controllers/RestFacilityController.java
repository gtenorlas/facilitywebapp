package ca.sheridancollege.controllers;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import ca.sheridancollege.DAO.FacilityDAO;

@RestController // specify that this class is a restful controller
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api/facility")
public class RestFacilityController {
	FacilityDAO facilityDAO = new FacilityDAO();

	public RestFacilityController() {
		CorsRegistry registry = new CorsRegistry();
		registry.addMapping("/api").allowedOrigins("*", "http://localhost:8080")
				.allowedMethods("POST", "GET", "PUT", "DELETE").allowedHeaders("Content-Type")
				.exposedHeaders("header-1", "header-2").allowCredentials(false).maxAge(6000);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET) // routing to home))
	public Object home(Model model) {

		return facilityDAO.getFacilities();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // routing to home
	public Object facilityItem(Model model, @PathVariable String id) {
		try {
			System.out.println("trying to get json object from facility");
			URL url = new URL("http://localhost:8080/com.spring/api/facility/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();

			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				Scanner sc = new Scanner(url.openStream());
				StringBuilder inline=new StringBuilder();
				while(sc.hasNext())
				{
					inline.append(sc.nextLine()+"\n");
				}
				System.out.println("\nJSON data in string format");
				System.out.println(inline);
				sc.close();
			}

		} catch (Exception e) {

		}

		return facilityDAO.getFacilities();
	}

}
