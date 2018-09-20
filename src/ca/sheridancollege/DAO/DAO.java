package ca.sheridancollege.DAO;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Court;
import ca.sheridancollege.beans.Facility;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserRole;


public class DAO {
	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();

	public User findByUserName(String username) {
		System.out.println("dao to retrieve user " + username);
		return (User) sessionFactory.openSession().createNamedQuery("User.getUserByUsername")
				.setParameter("username", username).getSingleResult();
	}

	public void createUser(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.flush();
		session.getTransaction().commit();
		session.close();
		
		System.out.println("User is saved in dao " + user.getUsername());
	}
	
	/*
	 * save userRole
	 */
	public void createUserRole(UserRole userRole) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(userRole);
		session.flush();
		session.getTransaction().commit();
		session.close();
		
		System.out.println("Role is saved in dao " + userRole.getRole());
	}

	/*
	 * Save Facility
	 */
	public void saveFacility(Facility facility) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(facility);
		session.flush();
		session.getTransaction().commit();
		session.close();
	}
	
	/*
	 * Save Court
	 * When booking is being save, an instance of court is called using it's id, then will be saved
	 */
	public void saveCourt(Court court) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(court);
		session.getTransaction().commit();
		session.close();
	}
	

	
	/*
	 * Get All facilities  to be viewed to facilities.jsp 
	 */
	public List<Facility> getFacilities() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Facility> criteria = criteriaBuilder.createQuery(Facility.class);
		Root<Facility> root = criteria.from(Facility.class);

		criteria.select(root);

		List<Facility> facilities = session.createQuery(criteria).getResultList();

		session.getTransaction().commit();
		session.close();

		return facilities;
	}

	/*
	 * Get facility based on username and then display the courts in the court.jsp
	 */
	public Facility getFacility(String username) {
		System.out.println("username is " +username);
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Facility> criteria = criteriaBuilder.createQuery(Facility.class);
		Root<Facility> root = criteria.from(Facility.class);
		

		//root.fetch("courts", JoinType.LEFT); // include the courts to fix the fetch lazy issue
		
		//Join courts = root.join("courts", JoinType.LEFT); //join bookings table
		//root.fetch("courts");
		
		root.fetch("courts", JoinType.LEFT); // include the courts to fix the fetch lazy issue
		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("username"), username));
		

		criteria.select(root);

		Facility facility = session.createQuery(criteria).getSingleResult();

		session.getTransaction().commit();
		session.close();
		
	
		return facility;
	}
	
	
	/*
	 * Get userrole with role "ROLE_USER"
	 */
	public UserRole getUserRole(String role) {
		System.out.println("role is " +role);
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<UserRole> criteria = criteriaBuilder.createQuery(UserRole.class);
		Root<UserRole> root = criteria.from(UserRole.class);
		


		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("role"), role));
		

		criteria.select(root);

		UserRole userRole = session.createQuery(criteria).getSingleResult();

		session.getTransaction().commit();
		session.close();
		
	
		return userRole;
	}
	
	/*
	 * Get facility based on username and then display the courts in the court.jsp
	 */
	public Facility getFacilityJustRegistered(String username) {
		System.out.println("username is " +username);
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Facility> criteria = criteriaBuilder.createQuery(Facility.class);
		Root<Facility> root = criteria.from(Facility.class);
		root.fetch("courts", JoinType.LEFT); // include the courts to fix the fetch lazy issue
		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("username"), username));

		Facility facility = session.createQuery(criteria).getSingleResult();

		session.getTransaction().commit();
		session.close();
		
	
		return facility;
	}

	/*
	 * Get all facilities Retrieved facilities are displayed in the court.jsp
	 */
	public Facility getFacility(int facilityId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Facility> criteria = criteriaBuilder.createQuery(Facility.class);
		Root<Facility> root = criteria.from(Facility.class);

		root.fetch("courts", JoinType.LEFT); // include the courts to fix the fetch lazy issue
		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("facilityId"), facilityId));

		Facility facility = session.createQuery(criteria).getSingleResult();

		session.getTransaction().commit();
		session.close();

		return facility;
	}

	/*
	 * Get a single court based off id
	 */
	public Court getCourt(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Court> criteria = criteriaBuilder.createQuery(Court.class);
		Root<Court> root = criteria.from(Court.class);

		root.fetch("bookings", JoinType.LEFT); // include the bookings to fix the fetch lazy issue
		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("courtNumber"), id));

		Court court = session.createQuery(criteria).getSingleResult();

		session.getTransaction().commit();
		session.close();

		return court;
	}

	/*
	 * Delete a single Court
	 */
	public void deleteCourt(Court court) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.delete(court);

		session.getTransaction().commit();
		session.close();

	}
	
	/*
	 * Get all facilities based on keyword
	 * the keyword will look facilityName and facilityDescription if it exists
	 * Retrieved facilities are displayed in the facilities.jsp
	 */
	public List<Facility> searchFacilities(String keyword) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		
		
		CriteriaQuery<Facility> criteria = criteriaBuilder.createQuery(Facility.class);
		Root<Facility> root = criteria.from(Facility.class);
		
		

		criteria.where(criteriaBuilder.or
			    (criteriaBuilder.like(root.get("facilityName"), "%" + keyword + "%"),
				criteriaBuilder.like(root.get("facilityDescription"), "%" + keyword + "%")));
		
		criteria.orderBy(criteriaBuilder.asc(root.get("facilityName")));
		
		
		List<Facility> facilityList = session.createQuery(criteria).getResultList();
		
		
		session.getTransaction().commit();
		session.close();
		
		return facilityList;
	}
	
	/*
	 * Get All bookings  to be viewed to be used in RestController
	 */
	public List<Booking> getAllBookings() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Booking> criteria = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteria.from(Booking.class);

		criteria.select(root);

		List<Booking> bookings = session.createQuery(criteria).getResultList();

		session.getTransaction().commit();
		session.close();

		return bookings;
	}
	
	/*
	 * Get All bookings  to be viewed to be used in RestController
	 */
	public Booking getBookingByID(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Booking> criteria = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteria.from(Booking.class);

		criteria.select(root);
		
		criteria.where(criteriaBuilder.equal(root.get("bookingId"), id));

		Booking booking = session.createQuery(criteria).getSingleResult();

		session.getTransaction().commit();
		session.close();

		return booking;
	}

}
