package com.bridgelabz.fundooapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooapi.dto.User;
import com.bridgelabz.fundooapi.services.UserService;
import com.bridgelabz.fundooapi.services.UserServiceImpl;


@RestController
public class UserController {
	@Autowired
	private UserService userService;



	@PostMapping(value = { "/signup" })
	public ResponseEntity<String> createUser(@RequestBody @Valid User user, BindingResult result) {
		return userService.registerUser(user,result);
	}


	@Autowired
	UserServiceImpl service;

	@GetMapping(value = { "/users" })
	public List<User> getUserList() {
		return service.getUserList();
	}


	@GetMapping("/register/activateuser")
	public ResponseEntity<String> activateUser(HttpServletRequest request) {
		String userId = request.getHeader("userId");
		System.out.println("ID is : " + userId);
		Integer uId = Integer.parseInt(userId);
		// Integer uId = Integer.parseInt(userId);
		try {
			userService.activateUser(uId);
		} catch (Exception E) {
			logger.info("User with Id: " + uId + " does not exist");
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		logger.info("User verified successfully !");
		return ResponseEntity.ok().body("");

	}



}