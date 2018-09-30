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
@Table(name="paymentVerification")
public class PaymentVerification implements Serializable {

	@Id
	@GeneratedValue
	private int verifiId;
	private String verifiDetails;
	private String authService;
}
