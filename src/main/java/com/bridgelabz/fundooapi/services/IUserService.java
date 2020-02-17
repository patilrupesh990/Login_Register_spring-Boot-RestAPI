package com.bridgelabz.fundooapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.model.UserLoginPair;
import com.bridgelabz.fundooapi.response.LoginResponse;
import com.bridgelabz.fundooapi.response.UserData;
import com.bridgelabz.fundooapi.response.UserResponse;


public interface IUserService{

	
	

	public ResponseEntity<UserResponse> registerUser(User user,BindingResult result);

	public ResponseEntity<String> activateUserAccount(String token);

	public ResponseEntity<LoginResponse> userLogin(UserLoginPair loginPair);   

	public ResponseEntity<String> verifyBeforeResetPassword(String email);
	
	public ResponseEntity<String> resetPassword(String newPassword,String token);
	public ResponseEntity<UserResponse> getUserList();
	public ResponseEntity<UserData> getUserByID(String token);





}