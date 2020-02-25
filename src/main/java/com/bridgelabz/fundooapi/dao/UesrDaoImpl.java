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
import com.bridgelabz.fundooapi.util.DateValidator;
import com.bridgelabz.fundooapi.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UesrDaoImpl implements IUserDao {
	@Autowired
	WebMvcConfig encryption;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private HibernateUtil<User> hibernateUtil;

	List<User> userList = new ArrayList<>();

	@Override
	public Integer registerUser(User user) {
		try {
		hibernateUtil.save(user);
		log.info("Svae user Object From Dao");
		return 1;
		}catch (Exception e) {
			log.info("Error in Dao");
			return 2;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean loginUser(String email, String password) {
//		Session session = entityManager.unwrap(Session.class);
//		// authentication logic
		List<User> userLis = null;
//		userLis = session.createQuery("from User").getResultList();
//		password = encryption.passwordEncoder().encode(password);
//		log.info("User entered password: " + password);
//		for (User tempUser : userLis)
//			if (tempUser.getEmail().equals(email)) {
//				log.info("dao" + tempUser.getPassword());
//				return tempUser.getPassword().equals(password);
//			}
//		return false;
		
		userLis=hibernateUtil.createQuery("from user").getResultList();
		for (User tempUser : userLis)
			if (tempUser.getEmail().equals(email)) {
				log.info("-------------------------------------------Found User From dao");
				log.info("dao" + tempUser.getPassword());
//				return tempUser.getPassword().equals(password);
				return false;
			}
		log.info("User Does not found in dao..");
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
			String hql = "update User set activate=:activ" + " " + " " + " where id = :id";
			TypedQuery<User> query = session.createQuery(hql);
			query.setParameter("activ", true);
			query.setParameter("id", i);
			int result = query.executeUpdate();
			transaction.commit();
			if (result > 0)
				log.info("User Verified SuccessFully in DAO");
		} catch (Exception e) {
			log.info("error in activate DAO", e);
		}
	}

	@Override
	public User getUserByEmail(String email) {
		
		log.info("reached in getUserByEmailDao successfully");
		
		Session session = entityManager.unwrap(Session.class);
		String hql = "from User where email=:mail";
		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery(hql);
		query.setParameter("mail", email);
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

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		log.info("reached in getAllUser successfully ");
		Session session = entityManager.unwrap(Session.class);
		@SuppressWarnings("rawtypes")
		CriteriaQuery  query = session.getCriteriaBuilder().createQuery(User.class);
		query.from(User.class);
		List<User> userLis = session.createQuery(query).getResultList();

		log.info("User Retrived from getAllUser Successfully...");
		return userLis;
	}

	@Override
	public int updateUserPaword(long id, String newPassword) {
		Transaction transaction = null;
		Session session = entityManager.unwrap(Session.class);
		int result=0;
		try {
			int i = (int) id;
			log.info("Retrived id from database::"+id);
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			TypedQuery<User> query = session.createQuery("update User set"+" password=:psw,"+"updateTime=:time" + " " + " " + " where id = :id");
			
			query.setParameter("psw", newPassword);
			query.setParameter("time", DateValidator.getCurrentDate());
			query.setParameter("id", i);
			 
			result = query.executeUpdate();
				
			log.info("Successfully Updated New Password from UserDao...");
			
		}catch (Exception e) {
				log.info("Failed to Updated Password from UserDao...",e);
			}
		return result;
	}

	@Override
	public User getUserById(Long id) {
		return hibernateUtil.getCurrentUser(id.intValue());

	}
	@Override
	public boolean isUserVerified(String email)
	{
		log.info("reached in getUserByEmail successfully");
		Session session = entityManager.unwrap(Session.class);
		@SuppressWarnings("unchecked")
		TypedQuery<User> query = session.createQuery("from User where email=:email");
		query.setParameter("email", email);
		User user=query.getSingleResult();
		if(user.isActivate())
			return true;
		else
			return false;
	}
	
	
	

}
