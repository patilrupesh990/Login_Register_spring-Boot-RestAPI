package com.bridgelabz.fundooapi.dao;


import java.util.List;

import com.bridgelabz.fundooapi.model.User;

public interface IUserDao 
{
	
	public Integer registerUser(User user);

	
	public boolean loginUser(String email, String password);

	
	public void activateUser(long id);

	
	public User getUserByEmail(String email);

	public boolean userExists(User user);

	public User getUserById(Long id);

	
	public int updateUserPaword(long id, String newPassword);
	public boolean isUserVerified(String email);
	public List<User> getAllUser();
}
