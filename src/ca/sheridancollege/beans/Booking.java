package ca.sheridancollege.beans;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="booking")
public class Booking implements Serializable {
	
	/**
	 * work with booking details
	 */
	@Id
	@GeneratedValue
	private int bookingId;
	private String customerEmail;
	private String customerName;
	private LocalDateTime bookingDate;
	private String bookingType;
	private String status;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private double duration;
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name="courtNumber", nullable=false)
    private Court court;
	private String comment;
	@JsonManagedReference
	@OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, 
    fetch = FetchType.LAZY, optional = true)
    private Payment payment;
	private String facilityName;
	private String faciltyAddress;
	private String courtName;
	
	public Booking (int bookingId) {
		this.bookingId=bookingId;
	}
	public Booking(String customerName, LocalDateTime bookingDate, String bookingType, String status, LocalDateTime startDateTime,
			LocalDateTime endDateTime, double duration, Court court, String comment) {
		super();
		this.customerName = customerName;
		this.bookingDate = bookingDate;
		this.bookingType = bookingType;
		this.status = status;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.duration=duration;
		this.court=court;
		this.comment = comment;
	}
	
	public Booking(String customerName, LocalDateTime bookingDate, String bookingType, String status, LocalDateTime startDateTime,
			LocalDateTime endDateTime, double duration, String comment) {
		super();
		this.customerName = customerName;
		this.bookingDate = bookingDate;
		this.bookingType = bookingType;
		this.status = status;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.duration=duration;
		this.comment = comment;
	}
	
	public static String[] status(){
		return new String[] {"","Active","Completed","Cancelled"};
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (bookingId != other.bookingId)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bookingId;
		return result;
	}
	
	/**
	 * 
	 * @param court
	 * @return cost for the court
	 */
	public String getCost(Court court) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		//return currencyFormat.format(this.duration * court.getPrice());
		return currencyFormat.format(this.payment.getSubTotal());
	}
	
	/**
	 * 
	 * @param date
	 * @return date as a string
	 */
	public static String formatDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, yyyy-MM-dd HH:mm a");
		
		

		// try to convert the string date into localdatetime
		try {
			return date.format( formatter);

		} catch (Exception e) {
			return "Please check date format entered.";
		}
	}

	
	
}
