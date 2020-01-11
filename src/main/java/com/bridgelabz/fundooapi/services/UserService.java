package com.bridgelabz.restapi.services;

import com.bridgelabz.restapi.model.User;


public interface UserService{
 
	public User findUserByEmail(String email);
	public void saveUser(User user);    
}
