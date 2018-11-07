package ca.sheridancollege.DAO;

import java.time.LocalDateTime;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Court;
/**
 * 
 * @author MAGS
 *
 */
public class CourtDAO {
	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();

	/*
	 * Save Court When booking is being save, an instance of court is called using
	 * it's id, then will be saved
	 */
	public boolean saveCourt(Court court) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.saveOrUpdate(court);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error saveCourt -> " + e);
			return false;
		} finally {
			session.close();
		}
		System.out.println("Done saving court");
		return true;
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
		// criteria.where(criteriaBuilder.equal(root.get("courtNumber"),(id)));
		// criteria.where(criteriaBuilder.isNull(root.get("endDate")));
		// Court court = null;
		criteria.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("courtNumber"), id),
				criteriaBuilder.isNull(root.get("endDate"))));
		// LocalDateTime endDate= court.getEndDate();
		// criteria.where(criteriaBuilder.equal(root.get("endDate"), null));
		Court court = null;
		try {
			court = session.createQuery(criteria).getSingleResult();
			System.out.println("query created ");
			session.getTransaction().commit();
		} catch (NoResultException nre) {
			System.out.println("NoResultExceptionError getCourt -> " + nre);
		} catch (Exception e) {
			System.out.println("Error getCourt -> " + e);
		} finally {
			session.close();
		}

		return court;
	}

	/*
	 * Delete a single Court
	 */
	/**
	 * @param court
	 * @return
	 */

	public boolean deleteCourt(Court court) {
		System.out.println("-- executing query --");
//		 EntityManagerFactory entityManagerFactory =
//		          Persistence.createEntityManagerFactory("example-unit");
//
//	      EntityManager em = entityManagerFactory.createEntityManager();
//	      Query query = em.createQuery("SELECT DISTINCT c FROM Court c LEFT JOIN FETCH c.bookings b");
//	      List<Court> courtList = query.getResultList();
//	
//	      em.close();
//	      return true;

		/*
		 * 
		 * Session session = sessionFactory.openSession(); session.beginTransaction();
		 * CriteriaBuilder cb = session.getCriteriaBuilder(); CriteriaQuery<Court> cq =
		 * cb.createQuery(Court.class); Root<Court> root = cq.from(Court.class);
		 * Fetch<Court, Booking> bookingFetch = root.fetch("bookings");
		 * System.out.println("booking fetch"); Join<Court, Booking> bookingJoin =
		 * root.join("bookings", JoinType.RIGHT); System.out.println("booking join ");
		 * cq.select(root);
		 * 
		 * cq.where(cb.lessThan(bookingJoin.get("startDateTime"), LocalDateTime.now()));
		 * // LocalDate date = null; //
		 * System.out.println(date.atStartOfDay().toLocalDate()); //
		 * cq.where(cb.greaterThan(bookingJoin.get("startDateTime"),date.atStartOfDay().
		 * toLocalDate())); // court.setEndDate(LocalDateTime.now());
		 * System.out.println("booking where "); Court courtToDeleteHasBookings = new
		 * Court(); try { courtToDeleteHasBookings =
		 * session.createQuery(cq).getSingleResult();
		 * System.out.println("booking exsites  "); // Path<Object> localDT =
		 * bookingJoin.get("startDateTime"); // if( localDT >= LocalDateTime.now())
		 * session.getTransaction().commit(); session.close(); return false; } catch
		 * (NoResultException nre) {
		 * System.out.println("NoResultExceptionError getCourt -> " + nre); }catch
		 * (Exception e) { System.out.println("Error getCourt -> " + e); }
		 * 
		 * System.out.println("Deleting court ");
		 * 
		 * court.setEndDate(LocalDateTime.now()); court.setAvailability("Inactive");
		 * this.saveCourt(court); session.close(); return true;
		 */

		LocalDateTime currentLocalDateTme = LocalDateTime.now();
		BookingDAO bookingDAO = new BookingDAO();
		boolean canDelete = bookingDAO.canDeleteCourt(court.getCourtNumber(), currentLocalDateTme);
		if (canDelete) {
			court.setEndDate(LocalDateTime.now());
			court.setAvailability("Inactive");

			this.saveCourt(court);
			return true;
		} else {
			return false;
		}

	}

//	public boolean deleteCourt(Court court) {
//		 System.out.println("-- executing query --");
////		 EntityManagerFactory entityManagerFactory =
////		          Persistence.createEntityManagerFactory("example-unit");
////
////	      EntityManager em = entityManagerFactory.createEntityManager();
////	      Query query = em.createQuery("SELECT DISTINCT c FROM Court c LEFT JOIN FETCH c.bookings b");
////	      List<Court> courtList = query.getResultList();
////	
////	      em.close();
////	      return true;
//		
//		 	Session session = sessionFactory.openSession();
//			session.beginTransaction();
//			CriteriaBuilder cb = session.getCriteriaBuilder();
//			CriteriaQuery<Court> cq = cb.createQuery(Court.class);
//			Root<Court> root=cq.from(Court.class);
//		    Fetch<Court, Booking> bookingFetch = root.fetch("bookings");
//		    System.out.println("booking fetch");
//			Join<Court, Booking> bookingJoin= root.join("bookings");
//			System.out.println("booking join ");
//			cq.select(root);
//			System.out.println(court.getCourtNumber());
//			System.out.println(bookingJoin.get("startDateTime"));
//			cq.where(cb.greaterThan(bookingJoin.get("startDateTime"),LocalDateTime.now()));
//			//LocalDate date = null;
//			//System.out.println(date.atStartOfDay().toLocalDate());
//			//cq.where(cb.greaterThan(bookingJoin.get("startDateTime"),date.atStartOfDay().toLocalDate()));
//			//court.setEndDate(LocalDateTime.now());
//			System.out.println("booking where ");
//			Court courtToDeleteHasBookings = null;
//			try {
//				courtToDeleteHasBookings = session.createQuery(cq).getSingleResult();
//				System.out.println("booking exsites  ");
//				session.getTransaction().commit();
//				session.close();
//				return false;
//			}
//			catch (NoResultException nre){
//			}
//			
//
//			//session.getTransaction().commit();
//			
//			System.out.println("Deleting court ");
//			
//	/*		Session session = sessionFactory.openSession();
//			session.beginTransaction();
//
//			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//			CriteriaQuery<Booking> criteria = criteriaBuilder.createQuery(Booking.class);
//			Root<Booking> root = criteria.from(Booking.class);
//
//			criteria.select(root);
//			criteria.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("courtNumber"), criteriaBuilder.isNull(root.get("endDate"))))) ;
//
//			Booking booking = session.createQuery(criteria).getSingleResult();
//			session.getTransaction().commit();
//			session.close();*/
//			
//			
//			
////			if (courtToDeleteHasBookings !=null) {
////				session.close();
////				return false;
////			}
//			
//			court.setEndDate(LocalDateTime.now());
//			court.setAvailability("Inactive");
//			this.saveCourt(court);
//			session.close();
//			return true;
//
//
//	}
//	

	/*
	 * Delete a single Court
	 */
	/**
	 * @param court
	 * @return
	 */
	/*
	 * public boolean deleteCourtOld(Court court) {
	 * 
	 * // some uses em instead of session as variable name on stack overflow Session
	 * session = sessionFactory.openSession(); session.beginTransaction();
	 * CriteriaBuilder cb = session.getCriteriaBuilder(); CriteriaQuery<Court> cq =
	 * cb.createQuery(Court.class); Root<Court> root = cq.from(Court.class);
	 * Fetch<Court, Booking> bookingFetch = root.fetch("bookings");
	 * System.out.println("booking fetch"); Join<Court, Booking> bookingJoin =
	 * root.join("bookings"); System.out.println("booking join "); cq.select(root);
	 * 
	 * //
	 * cq.where(cb.greaterThan(bookingJoin.get("startDateTime"),LocalDateTime.now())
	 * ); LocalDate date = null;
	 * System.out.println(date.atStartOfDay().toLocalDate());
	 * cq.where(cb.greaterThan(bookingJoin.get("startDateTime"),
	 * date.atStartOfDay().toLocalDate())); System.out.println("booking where ");
	 * 
	 * Court courtToDeleteHasBookings = session.createQuery(cq).getSingleResult();
	 * 
	 * session.getTransaction().commit();
	 * 
	 * System.out.println("Deleting court ");
	 * 
	 * /* Session session = sessionFactory.openSession();
	 * session.beginTransaction();
	 * 
	 * CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
	 * CriteriaQuery<Booking> criteria = criteriaBuilder.createQuery(Booking.class);
	 * Root<Booking> root = criteria.from(Booking.class);
	 * 
	 * criteria.select(root);
	 * criteria.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(
	 * "courtNumber"), criteriaBuilder.isNull(root.get("endDate"))))) ;
	 * 
	 * Booking booking = session.createQuery(criteria).getSingleResult();
	 * session.getTransaction().commit(); session.close();
	 */

	/*
	 * if (courtToDeleteHasBookings != null) { session.close(); return false; }
	 * 
	 * court.setEndDate(LocalDateTime.now()); this.saveCourt(court);
	 * session.close(); return true;
	 * 
	 * }
	 * 
	 * 
	 * public void setInactive(Court court) { Session session =
	 * sessionFactory.openSession(); session.beginTransaction();
	 * 
	 * CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
	 * CriteriaUpdate<Court> criteria =
	 * criteriaBuilder.createCriteriaUpdate(Court.class); Root<Court> root =
	 * criteria.from(Court.class);
	 * 
	 * // criteria.set(court.getEndDate(), LocalDateTime.now());
	 * 
	 * // criteria.select(root); int courtNumber = court.getCourtNumber();
	 * 
	 * criteria.where(criteriaBuilder.equal(root.get("courtNumber"), courtNumber));
	 * 
	 * session.createQuery(criteria).executeUpdate();
	 * 
	 * session.getTransaction().commit(); session.close();
	 * 
	 * }
	 */

}
