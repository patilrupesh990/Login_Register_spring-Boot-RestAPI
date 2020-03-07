package com.bridgelabz.fundooapi.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundooapi.model.Collaborator;
import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@Transactional
public class CollaboratorDaoImpl implements ICollaboratorDao{

	@Autowired
	HibernateUtil<Collaborator> hibernateUtil;
	
	@Override
	public boolean addCollaborator(int userId,Long noteId) {
		try {
		Collaborator col=new Collaborator();
		col.setNoteId(noteId);
		col.setUserId(userId);
		hibernateUtil.save(col);
		return true;
		}catch (Exception e) {
			log.info("error"+e);
			return false;
		}
	}
	
	@Override
	public List<Object> getNotes(Long userId) {
		String sql= "select notes.id,notes.color,notes.description,notes.is_archive,notes.is_pinned,notes.is_trashed,notes.title from notes left join collaborator on collaborator.note_id=notes.id where collaborator.user_id= :id";
		Query query =hibernateUtil.createNativeQuery(sql);
		query.setParameter("id", userId);
		 List<Object> notes=query.getResultList();
		 return notes;
	}
	@Override
	public List<User> getCollaboratorsList(Long noteId){
		String sql= "select * from user JOIN collaborator ON collaborator.user_id=user.id where collaborator.note_id= :nid ";
//		where  id=(select user_id from collaborator where note_id=:nid);
		Query<User> query=hibernateUtil.createUserNativeQuery(sql);
		query.setParameter("nid", noteId);
		return query.list();
	}
	@Override
	public int removeCollaborator(int userId,Long noteId) {
		String hql = "delete from Collaborator where userId=:uid AND noteId=:nid";
		Query query=hibernateUtil.createQuery(hql); 
		query.setParameter("uid", userId);
		 query.setParameter("nid", noteId);
		 return query.executeUpdate();
	}
}
