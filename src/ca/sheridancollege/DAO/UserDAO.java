package ca.sheridancollege.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
		System.out.println("role is " +role);
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
