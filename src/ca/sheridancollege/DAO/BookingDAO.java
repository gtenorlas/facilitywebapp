package ca.sheridancollege.DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Booking;
/**
 * 
 * @author MAGS
 *
 */
public class BookingDAO {
	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();

	/*
	 * Save Booking
	 */
	public boolean saveBooking(Booking booking) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.saveOrUpdate(booking);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	/*
	 * Save Booking
	 */
	public int saveBookingForAPI(Booking booking) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		int id = 0;
		try {
			id = (Integer) session.save(booking);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error saveBookingForAPI-> " + e);
		} finally {
			session.close();
		}
		return id;
	}

	/*
	 * Get All bookings to be viewed to be used in RestController
	 */
	public List<Booking> getAllBookings() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Booking> criteria = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteria.from(Booking.class);

		criteria.select(root);

		List<Booking> bookings = new ArrayList<Booking>();

		try {
			bookings = session.createQuery(criteria).getResultList();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getAllBookings -> " + e);
		} finally {
			session.close();
		}

		return bookings;
	}

	/*
	 * Get All bookings to be viewed to be used in RestController
	 */
	public List<Booking> getAllBookings(String email) {
		System.out.println("Getting emails by booking using DAO");
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Booking> criteria = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteria.from(Booking.class);
		root.fetch("payment", JoinType.LEFT); // include the bookings to fix the fetch lazy issue

		criteria.select(root);
		criteria.where(criteriaBuilder.equal(root.get("customerEmail"), email));

		List<Booking> bookings = new ArrayList<Booking>();

		try {
			bookings = session.createQuery(criteria).getResultList();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getAllBookings -> " + e);
		} finally {
			session.close();
		}

		System.out.println("bookings count " + bookings.size());
		return bookings;
	}

	/*
	 * Get All bookings to be viewed to be used in RestController
	 */
	public Booking getBookingByID(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Booking> criteria = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteria.from(Booking.class);

		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("bookingId"), id));

		Booking booking = null;
		try {
			booking = session.createQuery(criteria).getSingleResult();
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getAllBookingByID -> " + e);
		} finally {
			session.close();
		}

		return booking;
	}

	public List<Booking> searchBookings(String customerName, String status, LocalDateTime localDateTime) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Booking> criteria = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteria.from(Booking.class);
		LocalDateTime nextDay = null;
		try {
			nextDay = localDateTime.plusDays(1);

		} catch (Exception e) {

		}
		Predicate isDateMatchStartOfDay = criteriaBuilder.greaterThanOrEqualTo(root.get("startDateTime"),
				localDateTime);
		Predicate isDateMatchEndOfDay = criteriaBuilder.lessThanOrEqualTo(root.get("startDateTime"), nextDay);
		Predicate isStatusMatch = criteriaBuilder.equal(root.get("status"), status);
		Predicate isCustomerMatch = null;

		if (customerName != null) {
			if (customerName.contains("%")) {
				//customerName.replaceAll("\\*", "%");
				System.out.println("customerName is "+customerName);
				isCustomerMatch = criteriaBuilder.like(root.get("customerName"), customerName);
			} else {
				isCustomerMatch = criteriaBuilder.equal(root.get("customerName"), customerName);
			}
		}

		System.out.println("filtering");
		if (customerName == null && status != null && localDateTime != null) {
			System.out.println("option 1 filtering");

			criteria.where(criteriaBuilder.and(isDateMatchStartOfDay, isDateMatchEndOfDay, isStatusMatch));
		} else if (status == null && customerName != null && localDateTime != null) {
			System.out.println("option 2 filtering");

			criteria.where(criteriaBuilder.and(isDateMatchStartOfDay, isDateMatchEndOfDay, isCustomerMatch));
		} else if (localDateTime == null && customerName != null && status != null) {
			System.out.println("option 3 filtering");
			criteria.where(criteriaBuilder.and(isCustomerMatch, isStatusMatch));
		} else if (localDateTime == null && status == null && customerName != null) {
			System.out.println("option 4 filtering");
			criteria.where(isCustomerMatch);
		} else if (localDateTime == null && customerName == null && status != null) {
			System.out.println("option 5 filtering");
			criteria.where(isStatusMatch);
		} else if (localDateTime != null && customerName == null && status == null) {
			System.out.println("option 6 filtering");
			criteria.where(criteriaBuilder.and(isDateMatchStartOfDay, isDateMatchEndOfDay));
		} else if (localDateTime != null && customerName != null && status != null) {
			System.out.println("option 7 filtering");
			criteria.where(
					criteriaBuilder.and(isDateMatchStartOfDay, isDateMatchEndOfDay, isStatusMatch, isCustomerMatch));
		}

		List<Booking> bookings = new ArrayList<Booking>();

		try {
			bookings = session.createQuery(criteria).getResultList();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error searchBookings -> " + e);
		} finally {
			session.close();
		}

		return bookings;
	}

	// check when newStartDate is before startDate
//    if (newStartDateTimeLocal.isBefore(startDateTimeLocal)) {
//        if (newEndDateTimeLocal.isAfter(startDateTimeLocal)) {
//            System.out.println("Yes startDate is bad");
//        }
//    } else {
//        if (newStartDateTimeLocal.isBefore(endDateTimeLocal) || newStartDateTimeLocal.isEqual(startDateTimeLocal)) {
//            System.out.println("Yes startdate is bad within");
//        }
//    }
	public boolean bookingValidation(int courtNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Booking> criteria = cb.createQuery(Booking.class);
		Root<Booking> root = criteria.from(Booking.class);

		Predicate sameCourtNumber = cb.equal(root.get("court").get("courtNumber"), courtNumber);

		Predicate isStartDateBeforeStartDate = cb.greaterThanOrEqualTo(root.get("startDateTime"), startDateTime);
		Predicate isEndDateAfterStartDate = cb.lessThan(root.get("startDateTime"), endDateTime);

		Predicate isStartDateAfterStartDate = cb.lessThan(root.get("startDateTime"), startDateTime);
		Predicate isStartDateBeforeEndDate = cb.greaterThan(root.get("endDateTime"), startDateTime);

		// criteria.where(cb.and(sameCourtNumber,
		// isStartDateBeforeStartDate,isEndDateAfterStartDate));

		criteria.where(cb.or(cb.and(sameCourtNumber, isStartDateBeforeStartDate, isEndDateAfterStartDate),
				cb.and(sameCourtNumber, isStartDateAfterStartDate, isStartDateBeforeEndDate)));

		List<Booking> bookingList = null;
		try {
			bookingList = session.createQuery(criteria).getResultList();

			session.getTransaction().commit();
			session.close();
			System.out.println("booking validation returned " + bookingList.size());
			if (bookingList.size() == 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			session.close();
			return true;
		}
	}
	
	public boolean canDeleteCourt(int courtNumber, LocalDateTime currentDateTime) {
		System.out.println("checking canDeleteCourt in bookingDAO");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Booking> criteria = cb.createQuery(Booking.class);
		Root<Booking> root = criteria.from(Booking.class);

		Predicate sameCourtNumber = cb.equal(root.get("court").get("courtNumber"), courtNumber);
		Predicate hasFutureBookings = cb.greaterThanOrEqualTo(root.get("startDateTime"), currentDateTime);
		Predicate isActiveStatus = cb.equal(root.get("status"), "Active");
		criteria.where(cb.and(sameCourtNumber, hasFutureBookings,isActiveStatus));
		List<Booking> bookingList = null;
		try {
			bookingList = session.createQuery(criteria).getResultList();

			session.getTransaction().commit();
			session.close();
			System.out.println("booking validation returned " + bookingList.size());
			if (bookingList.size() == 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			session.close();
			return true;
		}
		
		
	}

}
