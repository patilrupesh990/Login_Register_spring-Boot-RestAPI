package com.bridgelabz.fundooapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.model.UserLoginPair;
import com.bridgelabz.fundooapi.services.UserService;
import com.bridgelabz.fundooapi.services.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired

	private UserService userService;
	@Autowired
	UserServiceImpl service;
	
	@GetMapping(value = { "/users" })
	public List<User> getUserList() {
		return service.getUserList();
	}

	@PostMapping("/registration")
	public ResponseEntity<String> createUser(@RequestBody User user, BindingResult result) {
		log.info("User Registration Controller");
		return userService.registerUser(user, result);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> userLogin(@RequestBody UserLoginPair loginPair) {
		log.info("User Registration Controller");
			return userService.userLogin(loginPair);
	}
	
	@PostMapping("/forgotpassword-verify-user")
	public ResponseEntity<String> forgotPasswordVerify(@RequestBody User userEmail)
	{
		log.info("Forgot Password verify from USer Controller Email:"+userEmail.getEmail());
		return userService.verifyBeforeResetPassword(userEmail.getEmail());
	}
	
	@GetMapping("/reset-password/{token}")
	public ResponseEntity<String> resetPassword(@PathVariable("token") String token,@RequestBody User newPassword) {
		log.info("Activate USer Controller password:"+newPassword);
			return userService.resetPassword(newPassword.getPassword(), token);
		}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/register/activateuser/{token}")
	public ResponseEntity<String> activateUser(@PathVariable("token") String token) {
		log.info("Activate USer Controller");

		long id = 0;
		try {
			id = userService.activateUserAccount(token);
		} catch (Exception E) {
			log.info("User with Id: " + id + " does not exist");
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		log.info("User verified successfully !");
		return ResponseEntity.ok().body("<h1 color:'red'>Verification Success!! Congratulations Your Accout Activated</h1>");
	}
	
	
	

}