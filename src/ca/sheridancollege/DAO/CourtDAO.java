package ca.sheridancollege.DAO;

import java.time.LocalDateTime;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Court;

public class CourtDAO {
	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();

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
		//Court court = null;
		criteria.where(criteriaBuilder.equal(root.get("courtNumber"), id));
		//LocalDateTime endDate= court.getEndDate();
		criteria.where(criteriaBuilder.equal(root.get("endDate"), null));

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
	public void setInactive(Court court) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaUpdate<Court> criteria = criteriaBuilder.createCriteriaUpdate(Court.class);
		Root<Court> root = criteria.from(Court.class);
		
		//criteria.set(court.getEndDate(), LocalDateTime.now());

		//criteria.select(root);
		int courtNumber = court.getCourtNumber();
		
		criteria.where(criteriaBuilder.equal(root.get("courtNumber"), courtNumber));

		  session.createQuery(criteria).executeUpdate();

		

		session.getTransaction().commit();
		session.close();
		
	}
	
}
