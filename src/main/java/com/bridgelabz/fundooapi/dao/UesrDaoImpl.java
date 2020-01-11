package com.bridgelabz.fundooapi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundooapi.configration.WebMvcConfig;
import com.bridgelabz.fundooapi.model.User;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UesrDaoImpl implements UserDao {
	Logger log = Logger.getLogger(UesrDaoImpl.class);
	@Autowired
	WebMvcConfig encryption;
	@Autowired
	private EntityManager entityManager;
	// private Session;
	List<User> userList = new ArrayList<>();

	@Override
	public Integer registerUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		log.info("Session Object" + session);
		Integer id = null;
		if (user.getPassword() != null) {
			user.setPassword(encryption.passwordEncoder().encode(user.getPassword()));
			user.setActivate("not verified");
			log.info("Encrypted password is: " + user.getPassword() + " length= " + user.getPassword().length());
		}
		log.info("User Object: " + user);
		id = (Integer) session.save(user);
		if (id == -1) {
			log.info("*****Id generated is: " + id);
			return -1;
		}
		log.info("Registration Successfull in DAO");
		return id;
	}

	@Override
	public boolean loginUser(String email, String password) {
		return false;
	}

	@Override
	public void activateUser(long id) {
		log.info("Activate USer DAO");
		try {
			log.info(id);
			int i=(int)id;
			Session session = entityManager.unwrap(Session.class);
			Transaction tx = session.beginTransaction();

			String hql = "update User set activate=:activ" + " " + " " + " where id = :i";
			Query query = session.createQuery(hql);
			query.setParameter("activ", "activate");
			query.setParameter("i", i);
			int result = query.executeUpdate();
			tx.commit();
			if (result > 0)
				log.info("User Verified SuccessFully in DAO");
		} catch (Exception e) {
			log.info("error in activate DAO");
			e.printStackTrace();
		}
	}

	@Override
	public User getUserByEmail(String email, User user) {
		return null;
	}

	@Override
	public boolean userExists(User user) {
		Session session = entityManager.unwrap(Session.class);
		log.info("sghdgh");

		String hql = "from User where email =:email or userName =:uname";
		Query query = session.createQuery(hql);
		query.setParameter("email", user.getEmail());
		query.setParameter("uname", user.getUserName());
		User userobj = (User) query.uniqueResult();
		if (userobj != null) {
			log.info("if v");
			return true;
		}
		return false;
	}

	@Override
	public User getUserById(Integer id, User user) {
		return null;
	}

	@Override
	public void resetPassword(User user) {

	}

}
