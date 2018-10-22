package ca.sheridancollege.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Customer;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserRole;

public class UserDAO {

	SessionFactory sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();

	public User findByUserName(String username) {
		System.out.println("dao to retrieve user " + username);
		return (User) sessionFactory.openSession().createNamedQuery("User.getUserByUsername")
				.setParameter("username", username).getSingleResult();
	}

	/*
	 * Get a single user based on username and password
	 */
	public User getUser(String username, String password) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		User user = null;

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);

		criteria.select(root);
		criteria.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("username"), username),
				criteriaBuilder.equal(root.get("password"), password)));
		try {
			user = session.createQuery(criteria).getSingleResult();
			System.out.println("query user by username and password");
			session.getTransaction().commit();
			session.close();
			return user;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			;
			session.close();
			return user;
		}

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
	 * Get userrole with role "ROLE_USER"
	 */
	public UserRole getUserRole(String role) {
		System.out.println("role is " + role);
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

}
