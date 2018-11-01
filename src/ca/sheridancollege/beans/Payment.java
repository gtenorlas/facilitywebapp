package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="payment")
public class Payment implements Serializable {

	@Id
	@GeneratedValue
	private int paymentId;
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookingId")
	private Booking booking;
	private double courtCharge; //hourly rate of the court
	private double adminFee; //top up cost for using the app
	private double subTotal; //courtCharge + adminFee
	private double taxPercentage; //tax in percentage
	private double taxAmount; //tax calculated based on subtotal
	private double totalAmount; //subTotal + taxAmount
	private LocalDateTime paymentDateTime;
	private String confirmationNumber;
	private String paymentMethod;
	private String status;
}
