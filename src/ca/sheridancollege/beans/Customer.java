package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customer")
public class Customer implements Serializable {
	@Id
	private String username;
	@OneToOne(fetch = FetchType.EAGER)
    @MapsId
	private User user;
	private String firstName;
	private String lastName;
	private String email;
	private String contactNumber;
	private LocalDateTime startDate;
	private LocalDateTime endDate;		
	private String status;
	private String originate;
	
	public Customer(User user, String firstName, String lastName, String email, String contactNumber, LocalDateTime startDate, LocalDateTime endDate, String status, String originate) {
		this.username = email;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contactNumber = contactNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.originate = originate;
	}
}
