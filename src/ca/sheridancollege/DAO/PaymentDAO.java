package ca.sheridancollege.DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Payment;

public class PaymentDAO {
	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();

	/*
	 * Save Payment
	 */
	public int savePayment(Payment payment) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		int id = 0;
		try {
			id = (Integer) session.save(payment);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error savePayment-> " + e);
		} finally {
			session.close();
		}
		return id;
	}

	public boolean updatePayment(Payment payment) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			session.update(payment);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error updatePayment-> " + e);
			return false;
		} finally {
			session.close();
		}
		return true;

	}

	/*
	 * Get All payments to be viewed to be used in RestController
	 */
	public List<Payment> getAllPayments() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Payment> criteria = criteriaBuilder.createQuery(Payment.class);
		Root<Payment> root = criteria.from(Payment.class);

		criteria.select(root);

		List<Payment> payments = new ArrayList<>();
		try {

			payments = session.createQuery(criteria).getResultList();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getAllPaymentst-> " + e);
		} finally {
			session.close();
		}

		return payments;
	}

	/*
	 * Get All payments to be viewed to be used in RestController
	 */
	public Payment getPaymentByID(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Payment> criteria = criteriaBuilder.createQuery(Payment.class);
		Root<Payment> root = criteria.from(Payment.class);

		criteria.select(root);

		criteria.where(criteriaBuilder.equal(root.get("paymentId"), id));

		Payment payment = null;
		try {
			payment = session.createQuery(criteria).getSingleResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error getPaymentByID-> " + e);
		} finally {
			session.close();
		}

		return payment;
	}

}
