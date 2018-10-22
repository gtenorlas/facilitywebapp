package ca.sheridancollege.DAO;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Court;
import ca.sheridancollege.beans.Customer;
import ca.sheridancollege.beans.User;

public class CustomerDAO {
	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();

	public void saveCustomer(Customer customer) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(customer);
		session.flush();
		session.getTransaction().commit();
		session.close();

		System.out.println("Customer is saved in dao " + customer.getFirstName());
	}

	/*
	 * Get a single customer
	 */
	public Customer getCustomer(String username, String password) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Customer customer = null;

		UserDAO userDAO = new UserDAO();
		User user = userDAO.getUser(username, password);
		if (user != null) {
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Customer> criteria = criteriaBuilder.createQuery(Customer.class);
			Root<Customer> root = criteria.from(Customer.class);
			root.fetch("user", JoinType.LEFT); // include the user to fix the fetch lazy issue
			criteria.select(root);
			criteria.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("user").get("username"), username),
					criteriaBuilder.isNull(root.get("endDate"))));
			try {
				customer = session.createQuery(criteria).getSingleResult();
				System.out.println("query created ");
				session.getTransaction().commit();
				session.close();
				return customer;
			}

			catch (NoResultException nre) {
				session.close();

				return customer;
			}
		}else {
			return customer;
		}

	}

}
