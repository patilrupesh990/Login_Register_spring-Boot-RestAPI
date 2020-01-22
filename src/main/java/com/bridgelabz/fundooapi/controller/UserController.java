package com.bridgelabz.fundooapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.model.UserLoginPair;
import com.bridgelabz.fundooapi.response.UserData;
import com.bridgelabz.fundooapi.response.UserResponse;
import com.bridgelabz.fundooapi.services.IUserService;
import com.bridgelabz.fundooapi.services.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@EnableEurekaClient
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired

	private IUserService userService;
	@Autowired
	UserServiceImpl service;

	@GetMapping(value = { "/list" })
	public ResponseEntity<UserResponse> getUserList() {
		return service.getUserList();

	}

	@PostMapping("/register")
	public ResponseEntity<UserResponse> createUser(@RequestBody User user, BindingResult result) {
		log.info("User Registration Controller");
		return userService.registerUser(user, result);
	}

	@PostMapping("/login")
	public ResponseEntity<String> userLogin(@RequestBody UserLoginPair loginPair) {
		log.info("User Registration Controller");
		return userService.userLogin(loginPair);
	}

	@PostMapping("/verify-user")
	public ResponseEntity<String> forgotPasswordVerify(@RequestBody User userEmail) {
		log.info("Forgot Password verify from USer Controller Email:" + userEmail.getEmail());
		return userService.verifyBeforeResetPassword(userEmail.getEmail());
	}

	@GetMapping("/reset-password/{token}")
	public ResponseEntity<String> resetPassword(@PathVariable("token") String token, @RequestBody User newPassword) {
		log.info("Activate USer Controller password:" + newPassword);
		return userService.resetPassword(newPassword.getPassword(), token);
	}

	@GetMapping("/register/activ/{token}")
	public ResponseEntity<String> activateUser(@PathVariable("token") String token) {
		log.info("Activate USer Controller");
		return userService.activateUserAccount(token);
	}

	@GetMapping("/{token}")
	public ResponseEntity<UserData> getUserById(@PathVariable("token") String token) {
		return service.getUserByID(token);
	}

}