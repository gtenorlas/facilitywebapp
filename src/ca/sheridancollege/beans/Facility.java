package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facility")
public class Facility implements Serializable {
	@Id
	@GeneratedValue
	private int facilityId;
	private String facilityName;
	@Column(name = "facilityDescription", length = 3000, columnDefinition="TEXT")
	@Type(type="text") 	//allow larger amount of characters in column description with text type
	private String facilityDescription; 
	
	//private FacilityAddress Address;
	
	@Column(name = "line_1", length = 255)
	private String line_1;
	@Column(name = "line_2", length = 255)
	private String line_2;
	@Column(name = "line_3", length = 255)
	private String line_3;
	private String city;
	private String province;
	private String postalCode;
	private String country;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Court> courts;
	
	private String username;
	
	private String contactNumber;
	private LocalDateTime creationDate;
	private LocalDateTime endDate;

}
