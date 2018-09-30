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
@Table(name="invitation")
public class Invitation implements Serializable {
	@Id
	@GeneratedValue
	private int eventId;
	private String eventName;
	private String eventDate;
	private String eventTime;
	private int customerId;
	private int friendId;
	private int bookingId;
	private String status;
}
