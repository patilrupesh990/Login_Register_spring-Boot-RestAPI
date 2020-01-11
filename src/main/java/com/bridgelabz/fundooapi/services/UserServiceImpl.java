package com.bridgelabz.fundooapi.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.bridgelabz.fundooapi.dao.UserDao;
import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.util.EmailGenerator;
import com.bridgelabz.fundooapi.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDAO;

	@Autowired
	private JwtTokenUtil generateToken;

	@Autowired
	private EmailGenerator emailGenerate;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByEmail(String email) {
		return null;
	}

	@Transactional
	@Override
	public ResponseEntity<String> registerUser(User user, BindingResult result) {
		log.info("User Registration Service");

		if (userDAO.userExists(user)) {
			log.info("User Alrady exist Service");
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("User Is Alrady Registered");

		}

		else if (result.hasErrors()) {
			log.info("Internal server error in Service");
			return ResponseEntity.status(HttpStatus.LOOP_DETECTED).body("Some Internal Error Occurred");
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setActivate("Not varified");
			user.setRegistration_date(LocalDate.now());
			log.info("User Registered");
			userDAO.registerUser(user);
			log.info("mail sending......");
			String link = "http://localhost:8085/user/register/activateuser/"
					+ generateToken.generateToken(user.getId());
			emailGenerate.sendEmail(user.getEmail(), "Foondu Notes Varification", link);
			log.info("MAil sent to user mailId");
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Verification Link Sent to your email" + user.getEmail() + " please verify your email first");

		}

	}

	public long activateUserAccount(String token) {
		log.info("Activate USer Service");

		long id = generateToken.parseToken(token);
		userDAO.activateUser(id);
		return id;
	}

	public List<User> getUserList() {
		return null;
	}

	@Override
	public User findUserByUserName(String userName) {
		return null;
	}

}
