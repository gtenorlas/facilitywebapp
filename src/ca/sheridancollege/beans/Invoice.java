package ca.sheridancollege.beans;

import java.io.Serializable;

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
@Table(name="invoice")
public class Invoice implements Serializable {
	@Id
	@GeneratedValue
	private int invoiceId;
	private Double amount;
	private String issuedDate;
	private String eventDesc;
	private int customerId;
	private int bookingId;
	private String status;
}
