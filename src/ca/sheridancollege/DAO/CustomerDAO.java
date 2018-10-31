package ca.sheridancollege.DAO;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
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
		session.saveOrUpdate(customer);
		session.flush();
		session.getTransaction().commit();
		session.close();

		System.out.println("Customer is saved in dao " + customer.getFirstName());
	}

	/*
	 * Get a single customer
	 */
	public Object getCustomer(String username, String password) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Customer customer = null;


			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Customer> criteria = criteriaBuilder.createQuery(Customer.class);
			Root<Customer> root = criteria.from(Customer.class);
			criteria.select(root);
			
			Predicate sameUsername = criteriaBuilder.equal(root.get("username"), username);
			Predicate isEndDateNull = criteriaBuilder.isNull(root.get("endDate"));
			
			criteria.where(criteriaBuilder.and(sameUsername,isEndDateNull));
			try {
				customer = session.createQuery(criteria).getSingleResult();
				System.out.println("query created ");
				session.getTransaction().commit();
				session.close();
				
				
				//check if password are the same
				if (Customer.checkPassword(password, customer.getPassword())) {
					return customer;
				}else {
					return "invalid";
				}
			}

			catch (NoResultException nre) {
				session.close();

				return customer;
			}


	}
	
	/*
	 * Get a single customer
	 */
	public Object getCustomer(String username) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Customer customer = null;


			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Customer> criteria = criteriaBuilder.createQuery(Customer.class);
			Root<Customer> root = criteria.from(Customer.class);
			criteria.select(root);
			
			Predicate sameUsername = criteriaBuilder.equal(root.get("username"), username);
			Predicate isEndDateNull = criteriaBuilder.isNull(root.get("endDate"));
			
			criteria.where(criteriaBuilder.and(sameUsername,isEndDateNull));
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


	}
	/*
	 * Get a single customer
	 */
	public Object getCustomerByEmail(String email) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Customer customer = null;
			System.out.println("Trying to get customer by email: "+ email);

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Customer> criteria = criteriaBuilder.createQuery(Customer.class);
			Root<Customer> root = criteria.from(Customer.class);
			criteria.select(root);
			
			Predicate sameEmail = criteriaBuilder.equal(root.get("email"), email);
			Predicate isEndDateNull = criteriaBuilder.isNull(root.get("endDate"));
			
			criteria.where(criteriaBuilder.and(sameEmail,isEndDateNull));
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


	}
	
	/*
	 * Get a single customer
	 */
	public boolean isDuplicate(String username) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Customer customer = null;


			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Customer> criteria = criteriaBuilder.createQuery(Customer.class);
			Root<Customer> root = criteria.from(Customer.class);
			criteria.select(root);
			
			Predicate sameUsername = criteriaBuilder.equal(root.get("username"), username);
			Predicate sameEmail = criteriaBuilder.equal(root.get("email"), username);
			//Predicate isEndDateNull = criteriaBuilder.isNull(root.get("endDate"));
			
			criteria.where(criteriaBuilder.or(sameUsername,sameEmail));
			try {
				customer = session.createQuery(criteria).getSingleResult();
				System.out.println("query created ");
				session.getTransaction().commit();
				session.close();
				
				if (customer==null) {
					return false;
				}else {
					return true;
				}
			}

			catch (NoResultException nre) {
				session.close();

				return false;
			}


	}
	
	
	
	
	


}
