package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="court")
public class Court implements Serializable {
	@Id
	@GeneratedValue
	private int courtNumber;
	private String courtName;
	private String availability;
	private int maxPlayer;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Booking> bookings=new ArrayList<Booking>();
	private String courtType;
	private Double price;
	@DateTimeFormat(pattern = "E yyyy-MM-dd hh:mm")
	private LocalDateTime creationDate;
	private LocalDateTime endDate;
	
	public static String[] availabilityTypes(){
		return new String[] {"Active","Inactive","Maintenance"};
	}
	
	public static String[] courtTypes(){
		return new String[] {"Basketball","Volleyball","Badminton", "Tennis", "Hockey"};
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Court other = (Court) obj;
		if (courtNumber != other.courtNumber)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courtNumber;
		return result;
	}
	
	
	
}
