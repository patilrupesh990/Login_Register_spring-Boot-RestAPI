package com.bridgelabz.fundooapi.util;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundooapi.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@SuppressWarnings("unchecked")
@Slf4j
public class HibernateUtil<T> 
{
	@Autowired 
	private EntityManager entityManager;
	
	
	public T save(T object)
	{
		Session session = entityManager.unwrap(Session.class);
		return (T) session.save(object);
	}
	
	public Query<T> createQuery(String query)
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(query);
	}
	
	public void update(T object)
	{
		Session session = entityManager.unwrap(Session.class);
		 session.update(object);
	}
	public T updateAndGetObject(String query)
	{
		Session session = entityManager.unwrap(Session.class);
		return (T) session.createQuery(query).list().stream().findFirst();
	}
	
	public Query<T> select(String query)
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(query);
	}
	public T getCurrentUser(Serializable value)
	{
		Session session = entityManager.unwrap(Session.class);
		return (T) session.get(User.class,value);
	}
	
	public boolean getUserById(Long id) {
		log.info("incoming value:"+id);
		Session session = entityManager.unwrap(Session.class);	
		Query q = session.createNativeQuery("SELECT a.id FROM user a");
		List<Object> users = q.getResultList();
		 try {
		for (Object a : users) {
			log.info("value of a:"+a);
			Integer value=(Integer) a;
			log.info("true/false:"+id.equals(Long.valueOf(value)));

			if(id.equals(Long.valueOf(value)))
			{
			  return true;
			}
		}
		 }catch (Exception e) {
			 e.printStackTrace();
			 System.out.println(e);
		}
		return false;
	}
}
