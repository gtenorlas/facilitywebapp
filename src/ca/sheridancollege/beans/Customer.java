package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customer")
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8624850542956011867L;
	@Id
	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String username;
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	@Column(name = "firstname", unique = false, nullable = false, length = 255)
	private String firstName;
	@Column(name = "lastname", unique = false, nullable = false, length = 255)
	private String lastName;
	private String email;
	@Column(name = "contactnumber", unique = false, nullable = false, length = 45)
	private String contactNumber;
	private LocalDateTime startDate;
	private LocalDateTime endDate;		
	private String status;
	private String originate;
	
	public Customer(String firstName, String lastName, String email, String contactNumber, LocalDateTime startDate, LocalDateTime endDate, String status, String originate) {
		this.username = email;
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
