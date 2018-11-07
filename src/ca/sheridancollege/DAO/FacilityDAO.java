package ca.sheridancollege.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Facility;

public class FacilityDAO {

	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();

	/*
	 * Save Facility
	 */
	public void saveFacility(Facility facility) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.saveOrUpdate(facility);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error saveFacility-> " + e);

		} finally {
			session.close();
		}
	}

	/*
	 * Get All facilities to be viewed to facilities.jsp
	 */
	public List<Facility> getFacilities() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Facility> criteria = criteriaBuilder.createQuery(Facility.class);
		Root<Facility> root = criteria.from(Facility.class);
		root.fetch("courts", JoinType.LEFT);
		criteria.select(root);
		List<Facility> facilities = new ArrayList<>();

		try {
			facilities = session.createQuery(criteria).getResultList();

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getFacilities-> " + e);
		} finally {
			session.close();
		}

		return facilities;
	}

	/*
	 * Get facility based on username and then display the courts in the court.jsp
	 */
	public Facility getFacility(String username) {
		System.out.println("username is " + username);
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Facility> criteria = criteriaBuilder.createQuery(Facility.class);
		Root<Facility> root = criteria.from(Facility.class);

		// root.fetch("courts", JoinType.LEFT); // include the courts to fix the fetch
		// lazy issue

		// Join courts = root.join("courts", JoinType.LEFT); //join bookings table
		// root.fetch("courts");

		root.fetch("courts", JoinType.LEFT); // include the courts to fix the fetch lazy issue
		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("username"), username));

		criteria.select(root);

		Facility facility = null;

		try {
			facility = session.createQuery(criteria).getSingleResult();

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getFacility-> " + e);
		} finally {
			session.close();
		}

		return facility;
	}

	/*
	 * Get facility based on username and then display the courts in the court.jsp
	 */
	public Facility getFacilityJustRegistered(String username) {
		System.out.println("username is " + username);
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Facility> criteria = criteriaBuilder.createQuery(Facility.class);
		Root<Facility> root = criteria.from(Facility.class);
		root.fetch("courts", JoinType.LEFT); // include the courts to fix the fetch lazy issue
		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("username"), username));

		Facility facility = null;
		try {

			facility = session.createQuery(criteria).getSingleResult();

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getFacilityJustRegistered-> " + e);
		} finally {
			session.close();
		}

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

		Facility facility = null;

		try {
			facility = session.createQuery(criteria).getSingleResult();

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getFacily by facilityId-> " + e);
		} finally {
			session.close();
		}

		return facility;
	}

	/*
	 * Get all facilities based on keyword the keyword will look facilityName and
	 * facilityDescription if it exists Retrieved facilities are displayed in the
	 * facilities.jsp
	 */
	public List<Facility> searchFacilities(String keyword) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Facility> criteria = criteriaBuilder.createQuery(Facility.class);
		Root<Facility> root = criteria.from(Facility.class);

		criteria.where(criteriaBuilder.or(criteriaBuilder.like(root.get("facilityName"), "%" + keyword + "%"),
				criteriaBuilder.like(root.get("facilityDescription"), "%" + keyword + "%")));

		criteria.orderBy(criteriaBuilder.asc(root.get("facilityName")));

		List<Facility> facilityList = new ArrayList<>();
		try {
			facilityList = session.createQuery(criteria).getResultList();

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error searchFacilities-> " + e);
		} finally {
			session.close();
		}

		return facilityList;
	}
}
