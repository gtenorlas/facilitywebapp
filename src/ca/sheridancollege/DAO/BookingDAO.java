package ca.sheridancollege.DAO;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Booking;

public class BookingDAO {
	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();

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
