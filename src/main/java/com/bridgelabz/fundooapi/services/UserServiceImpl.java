package com.bridgelabz.fundooapi.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.bridgelabz.fundooapi.configration.WebMvcConfig;
import com.bridgelabz.fundooapi.dao.UserDao;
import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.model.UserLoginPair;
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
	WebMvcConfig encryption;

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
			user.setPassword(encryption.passwordEncoder().encode(user.getPassword()));
			user.setActivate("Not varified");
			user.setRegistration_date(LocalDate.now());
			log.info("User Registered");
			userDAO.registerUser(user);
			log.info("mail sending......");
			String link = "http://192.168.1.175:8085/user/register/activateuser/"
					+ generateToken.generateToken(user.getId());
			emailGenerate.sendEmail(user.getEmail(), "Foondu Notes Varification", link);
			log.info("MAil sent to user mailId");
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Verification Link Sent to your email" + user.getEmail() + " please verify your email first");
		}
	}

	@Override
	public ResponseEntity<String> userLogin(UserLoginPair loginPair) {
		User user = userDAO.getUserByEmail(loginPair.getEmail());

		log.info("UserInput password :" + loginPair.getPassword());
		log.info("encrypted :" + user.getPassword());

		log.info("password matcher:"
				+ encryption.passwordEncoder().matches(loginPair.getPassword(), user.getPassword()));
		if (user.getEmail().equals(loginPair.getEmail())
				&& encryption.passwordEncoder().matches(loginPair.getPassword(), user.getPassword())) {
			log.info("Successfully LogedIn...");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login SuccessFully Wellcome:" + user.getUserName());

		} else {
			log.info("password invalid username and password");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid User Name Or Password");
		}
	}

	public long activateUserAccount(String token) {
		log.info("Activate USer Service");
		long id = generateToken.parseToken(token);
		userDAO.activateUser(id);
		return id;
	}

	@Override
	public ResponseEntity<String> verifyBeforeResetPassword(String email) {
		User user = new User();
		try {
			user = userDAO.getUserByEmail(email);
			log.info("Fetched user Email from DAO:" + user.getEmail());
		} catch (NullPointerException e) {
			log.info("user does not exists from service");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Does Not exist");
		}
		if (email.equals(user.getEmail())) {
			log.info("User Exist from service");
			log.info("mail sending For reset Password......");
			String link2 = "http://192.168.1.175:8085/user/verify/" + generateToken.generateToken(user.getId());
			emailGenerate.sendEmail(user.getEmail(), "FundooNotes Api Forgot Password Request", link2);
			log.info("mail sent For reset Password......");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Exists Enter new Password");
		} else {
			log.info("user does not exists from service");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Does Not exist");
		}
	}

	public List<User> getUserList() {
		return null;
	}

	@Override
	public User findUserByUserName(String userName) {
		return null;
	}

}
