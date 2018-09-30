package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	@GeneratedValue
	private int customerId;
	private String customerName;
	private String address;
	private String email;
	private int contactNumber;
	private String contactNumner;
	private String dateOfBirth;
	private String status;

}
