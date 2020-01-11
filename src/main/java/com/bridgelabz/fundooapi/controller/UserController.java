package com.bridgelabz.fundooapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.jboss.logging.Logger;
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

import com.bridgelabz.fundooapi.dao.UesrDaoImpl;
import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.services.UserService;
import com.bridgelabz.fundooapi.services.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

	Logger log = Logger.getLogger(UesrDaoImpl.class);
	@Autowired

	private UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<String> createUser(@RequestBody @Valid User user, BindingResult result) {
		System.out.println("loda1");

		return userService.registerUser(user, result);
	}

	@Autowired
	UserServiceImpl service;

	@GetMapping(value = { "/users" })
	public List<User> getUserList() {
		return service.getUserList();
	}

	@GetMapping("/register/activateuser/{token}")
	public ResponseEntity<String> activateUser(@PathVariable("token") String token) {
		log.info("Activate USer Controller");

		long id = 0;
		try {
			id = userService.activateUserAccount(token);
		} catch (Exception E) {
			log.info("User with Id: " + id + " does not exist");
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		log.info("User verified successfully !");
		return ResponseEntity.ok().body("<h1 color:red>Verification Success!! Congratulations Your Accout Activated</h1>");
	}
	

}