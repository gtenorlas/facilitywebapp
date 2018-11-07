package ca.sheridancollege.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author MAGS
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name="User.getUserByUsername", query="from User where username = :username")
public class User implements Serializable {

	/**
	 * work with application user
	 */
	@Id
	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String username;
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	@Column(name = "status", nullable = false)
	private String status;
	@Column(name = "confirmationtoken", nullable = false)
	private String confirmationToken;
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "user")
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	
	
	public User(String username, String password, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.status = "pending";
		this.confirmationToken="";
	}
	
	
}
