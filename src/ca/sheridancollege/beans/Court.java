package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * Cascade = "all" means to apply all primary cascade types. As of Hibernate 5.3, these types are:

"delete" / "remove",
"detach" / "evict",
"merge",
"lock",
"persist",
"refresh",
"replicate",
"save_update" / "update"


CREATE TABLE `Cart` (
  `cart_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
 
CREATE TABLE `Items` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cart_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cart_id` (`cart_id`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `Cart` (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;


It is importing to note that the @ManyToOne annotation is associated with Court class variable. @JoinColumn annotation references the mapped column.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="court")
public class Court implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "courtNumber")
	private int courtNumber;
	private String courtName;
	private String availability;
	private int maxPlayer;
	@JsonManagedReference
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="court", cascade = {CascadeType.ALL})
	///private Set<Booking> bookings=new HashSet<Booking>();
	private List<Booking> bookings=new ArrayList<Booking>();
	private String courtType;
	private Double price;
	@DateTimeFormat(pattern = "E yyyy-MM-dd hh:mm")
	private LocalDateTime creationDate;
	private LocalDateTime endDate;
	
	public static String[] availabilityTypes(){
		return new String[] {"Active","Maintenance"}; //removed Inactive because there is a delete button that makes it Inactive
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
