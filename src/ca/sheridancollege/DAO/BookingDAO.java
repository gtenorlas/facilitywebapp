package ca.sheridancollege.DAO;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Facility;

public class BookingDAO {
	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();
	
	/*
	 * Save Booking
	 */
	public void saveBooking(Booking booking) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(booking);
		session.flush();
		session.getTransaction().commit();
		session.close();
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

	public List<Booking> searchBookings(String customerName, String status, LocalDateTime localDateTime) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		
		
		CriteriaQuery<Booking> criteria = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteria.from(Booking.class);
		
//		LocalDateTime localDateTime = null;
//		if (startDT!=null) {
//			localDateTime = LocalDateTime.parse(startDT);
//
//		}
		
		if(customerName == null && status != null && localDateTime != null) {
			criteria.where(criteriaBuilder.and(
					criteriaBuilder.like(root.get("status"), "%" + status + "%"),
					criteriaBuilder.like(root.get("startDateTime"), "%" + localDateTime + "%")
					));
		}
		else if (status == null && customerName != null && localDateTime != null) {
			criteria.where(criteriaBuilder.and
				    (criteriaBuilder.like(root.get("customerName"), "%" + customerName + "%"),
					criteriaBuilder.like(root.get("startDateTime"), "%" + localDateTime + "%")
					));
		}
		else if (localDateTime == null && customerName != null && status != null) {
			criteria.where(criteriaBuilder.and
				    (criteriaBuilder.like(root.get("status"), "%" + status + "%"),
					criteriaBuilder.like(root.get("startDateTime"), "%" + localDateTime + "%")
					));
		}
		else if (localDateTime == null && status == null && customerName != null) {
			criteria.where(criteriaBuilder.and
				    (criteriaBuilder.like(root.get("customerName"), "%" + customerName + "%")
					));
		}
		else if (localDateTime == null && customerName == null && status != null) {
			criteria.where(criteriaBuilder.and
				    (criteriaBuilder.like(root.get("status"), "%" + status + "%")
					));
		}
		else if (localDateTime != null && customerName == null && status == null) {
			criteria.where(criteriaBuilder.and
				    (criteriaBuilder.like(root.get("startDateTime"), "%" + localDateTime + "%")
					));
		}
		else if (localDateTime != null && customerName != null && status != null) {
			criteria.where(criteriaBuilder.and
					(criteriaBuilder.like(root.get("customerName"), "%" + customerName + "%"),
							criteriaBuilder.like(root.get("status"), "%" + status + "%"),
							criteriaBuilder.like(root.get("startDateTime"), "%" + localDateTime + "%")
							));
		}

//		criteria.where(criteriaBuilder.and
//			    (criteriaBuilder.like(root.get("customerName"), "%" + customerName + "%"),
//				//criteriaBuilder.like(root.get("bookingType"), "%" + courtName + "%"),
//				criteriaBuilder.like(root.get("status"), "%" + status + "%"),
//				criteriaBuilder.like(root.get("startDateTime"), "%" + startDT + "%")
//				));
		
		//criteria.orderBy(criteriaBuilder.asc(root.get("facilityName")));
		
		
		List<Booking> bookingList = session.createQuery(criteria).getResultList();
		
		
		session.getTransaction().commit();
		session.close();
		
		return bookingList;
	}

}
