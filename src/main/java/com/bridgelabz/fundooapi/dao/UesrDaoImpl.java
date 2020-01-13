package com.bridgelabz.fundooapi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundooapi.configration.WebMvcConfig;
import com.bridgelabz.fundooapi.model.User;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UesrDaoImpl implements UserDao {
	@Autowired
	WebMvcConfig encryption;
	@Autowired
	private EntityManager entityManager;

	List<User> userList = new ArrayList<>();

	@Override
	public Integer registerUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		log.info("Session Object" + session);
		Integer id = null;

		log.info("User Object: " + user);
		id = (Integer) session.save(user);
		if (id == -1) {
			log.info("*****Id generated is: " + id);
			return -1;
		}
		log.info("Registration Successfull in DAO");
		return id;
	}

	@SuppressWarnings("unchecked")
	public boolean loginUser(String email, String password) {
		Session session = entityManager.unwrap(Session.class);
		// authentication logic
		List<User> userLis = null;
		userLis = session.createQuery("from User").getResultList();
		password = encryption.passwordEncoder().encode(password);
		log.info("User entered password: " + password);
		for (User tempUser : userLis)
			if (tempUser.getEmail().equals(email)) {
				log.info("dao" + tempUser.getPassword());
				return tempUser.getPassword().equals(password);
			}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void activateUser(long id) {
		log.info("Activate USer DAO");
		Transaction transaction = null;
		try {
			int i = (int) id;
			Session session = entityManager.unwrap(Session.class);
			transaction = session.beginTransaction();

			String hql = "update User set activate=:activ" + " " + " " + " where id = :i";
			TypedQuery<User> query = session.createQuery(hql);
			query.setParameter("activ", "activate");
			query.setParameter("i", i);
			int result = query.executeUpdate();
			transaction.commit();
			if (result > 0)
				log.info("User Verified SuccessFully in DAO");
		} catch (Exception e) {
			// transaction.rollback();
			log.info("error in activate DAO", e);
		}
	}

	@Override
	public User getUserByEmail(String email) {
		log.info("reached in getUserByEmail successfully");
		Session session = entityManager.unwrap(Session.class);
		String hql = "from User where email=:email";
		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery(hql);
		query.setParameter("email", email);
		return query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean userExists(User user) {
		Session session = entityManager.unwrap(Session.class);
		log.info("reached in userExists successfully ");

		String hql = "from User where email =:email or userName =:uname";
		Query<User> query = session.createQuery(hql);
		query.setParameter("email", user.getEmail());
		query.setParameter("uname", user.getUserName());
		User userobj = query.uniqueResult();
		if (userobj != null) {
			log.info("if v");
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		log.info("reached in getAllUser successfully ");
		Session session = entityManager.unwrap(Session.class);
		@SuppressWarnings("rawtypes")
		CriteriaQuery query;

		query = session.getCriteriaBuilder().createQuery(User.class);
		query.from(User.class);
		List<User> userLis = session.createQuery(query).getResultList();

		log.info("User Retrived from getAllUser Successfully...");
		return userLis;
	}

	public void updateUserPassword(String userEmail, String newPassword) {
		Session session = entityManager.unwrap(Session.class);
		Transaction transaction = session.beginTransaction();
		try {
			String hql = "update User set password=:password" + " " + " " + " where email = :email";
			@SuppressWarnings("unchecked")
			TypedQuery<User> query = session.createQuery(hql);
			query.setParameter("password", newPassword);
			query.setParameter("email", userEmail);
			int result = query.executeUpdate();
			if (result > 0) {
				transaction.commit();
			} else
				log.info("Failed to Update New Password from UserDao...");
		} catch (Exception e) {
			log.info("Failed to Update New Password from UserDao...", e);
		}
	}

	@Override
	public User getUserById(Long id) {
		log.info("reached in getUserByEmail successfully");
		Session session = entityManager.unwrap(Session.class);
		String hql = "from User where id=:id";
		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery(hql);
		query.setParameter("id", id);
		return query.uniqueResult();
	}

	@Override
	public void resetPassword(User user) {

	}

}
