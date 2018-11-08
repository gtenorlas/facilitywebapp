package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author MAGS
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facility")
public class Facility implements Serializable {
	/**
	 * Work with facility details
	 */
	@Id
	@GeneratedValue
	@Column(name = "facilityId", updatable = false, nullable = false)
	private int facilityId;
	@NotEmpty(message = "Facility cannot be empty")
	@Size(min=3, max=255, message="Facility name must be between 3 to 255 characters")
	private String facilityName;
	@NotEmpty(message = "Facility description cannot be empty")
	@Size(min=50,max=3000, message="Facility description must be between 50 to 3000 characters")
	@Column(name = "facilityDescription", length = 3000, columnDefinition="TEXT")
	@Type(type="text") 	//allow larger amount of characters in column description with text type
	private String facilityDescription; 
	@NotEmpty(message = "Address line 1 cannot be empty")
	@Size(max=255, message="Address line 1 cannot be more 255 characters")
	@Column(name = "line_1", length = 255)
	private String line_1;
	@Size(max=255, message="Address line 2 cannot be more 255 characters")
	@Column(name = "line_2", length = 255)
	private String line_2;
	@Size(max=255, message="Address line 3 cannot be more 255 characters")
	@Column(name = "line_3", length = 255)
	private String line_3;
	@NotEmpty(message = "City cannot be empty")
	@Size(max=255, message="City cannot be more than 255 characters")
	private String city;
	@Size(max=255, message="Province cannot be more than 255 characters")
	private String province;
	@Size(max=20, message="Postal code cannot be more than 20 characters")
	private String postalCode;
	@NotEmpty(message = "Country cannot be empty")
	@Size(max=255, message="Country cannot be more than 255 characters")
	private String country;
	@Autowired(required = false)
	@JsonManagedReference
	@OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, 
		    fetch = FetchType.LAZY)
	private List<Court> courts;
	
	private String username;
	
	@NotEmpty(message = "Contact number cannot be empty")
	@Size(max=20, message="Contact number cannot be more than 20 characters")
	private String contactNumber;
	private LocalDateTime creationDate;
	private LocalDateTime endDate;
	private String lat;
	private String lng;
	
	/**
	 * 
	 * @return country name list
	 */
	public static Object countryList() {
		String[] countryCodes = Locale.getISOCountries();
		ArrayList<String> countryNames = new ArrayList<>();

		for (String countryCode : countryCodes) {

		    Locale obj = new Locale("", countryCode);

		    System.out.println("Country Code = " + obj.getCountry() 
		        + ", Country Name = " + obj.getDisplayCountry());
		    countryNames.add(obj.getDisplayCountry());

		}
		Collections.sort(countryNames); 
		
		return countryNames;
	}

}
