package ca.sheridancollege.beans;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="booking")
public class Booking implements Serializable {
	@Id
	@GeneratedValue
	private int bookingId;
	private String customerName;
	private LocalDateTime bookingDate;
	private String bookingType;
	private String status;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private double duration;
	@ManyToOne
    @JoinColumn(name="courtNumber", nullable=false)
    private Court court;
	private String comment;
	
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
	
	public String getCost(Court court) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		return currencyFormat.format(this.duration * court.getPrice());
	}

	
	
}
