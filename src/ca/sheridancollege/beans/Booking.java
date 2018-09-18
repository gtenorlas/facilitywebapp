package ca.sheridancollege.beans;

import java.io.Serializable;
import java.time.Instant;
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
	
	public Booking (int bookingId) {
		this.bookingId=bookingId;
	}
	
	public Booking(String customerName, LocalDateTime bookingDate, String bookingType, String status, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		super();
		this.customerName = customerName;
		this.bookingDate = bookingDate;
		this.bookingType = bookingType;
		this.status = status;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
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

	
	
}
