package com.bridgelabz.fundooapi.dao;

import java.util.List;

import com.bridgelabz.fundooapi.model.User;

public interface ICollaboratorDao {
	public boolean addCollaborator(int userId,Long noteId);

	public List<Object> getNotes(Long userId);
	public List<User> getCollaboratorsList(Long noteId);
	public int  removeCollaborator(int userId,Long noteId);
}
