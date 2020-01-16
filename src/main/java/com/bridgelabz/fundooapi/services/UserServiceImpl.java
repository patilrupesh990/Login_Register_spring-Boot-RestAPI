package com.bridgelabz.fundooapi.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.bridgelabz.fundooapi.configration.WebMvcConfig;
import com.bridgelabz.fundooapi.dao.IUserDao;
import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.model.UserLoginPair;
import com.bridgelabz.fundooapi.util.DateValidator;
import com.bridgelabz.fundooapi.util.EmailGenerator;
import com.bridgelabz.fundooapi.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service("userService")
@Slf4j
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDAO;

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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some Internal Error Occurred");
		} else {
			user.setPassword(encryption.passwordEncoder().encode(user.getPassword()));
			user.setActivate("Not varified");
			user.setCreationTime(DateValidator.getCurrentDate());
			log.info("dateofbith:"+user.getDateOfBirth());
			
				log.info("User Registered");
				userDAO.registerUser(user);
				log.info("mail sending......");
				String link = "http://192.168.1.175:8085/user/register/activateuser/"
						+ generateToken.generateToken(user.getId());
				emailGenerate.sendEmail(user.getEmail(), "Foondu Notes Varification", link);
				log.info("MAil sent to user mailId");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Verification Link Sent to your email ===>"
						+ user.getEmail() + "<=== please verify your email first");
			
			
		}
	}

	@Override
	public ResponseEntity<String> userLogin(UserLoginPair loginPair) {
		
		Optional<User> userDetails=Optional.of(userDAO.getUserByEmail(loginPair.getEmail()));
		
		log.info("UserInput password :" + loginPair.getPassword());
		log.info("encrypted :" + userDetails.get().getPassword());

		log.info("password matcher:"
				+ encryption.passwordEncoder().matches(loginPair.getPassword(), userDetails.get().getPassword()));

		if (userDetails.get().getPassword().equals(loginPair.getEmail())
				&& encryption.passwordEncoder().matches(loginPair.getPassword(), userDetails.get().getPassword())) {
			if (!userDAO.isUserVerified(loginPair.getEmail())) {
				log.info("Successfully LogedIn...");
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body("Login SuccessFully Wellcome:" + userDetails.get().getPassword());
			} else {
				return ResponseEntity.status(HttpStatus.LOCKED).body("Your Account is Not Activated");
			}
		} else {
			log.info("password invalid username and password");

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid User Name Or Password");
		}

	}

	public long activateUserAccount(String token) {
		log.info("Activate USer Service");
		userDAO.activateUser(getUserIdFromToken(token));
		return getUserIdFromToken(token);
	}

	public long getUserIdFromToken(String token) {
		return generateToken.parseToken(token);
	}

	@Override
	public ResponseEntity<String> verifyBeforeResetPassword(String email) {
		Optional<User> userDetails=Optional.of(userDAO.getUserByEmail(email));
		
		try {
			log.info("Fetched user Email from DAO:" + userDetails.get().getEmail());
		} catch (NullPointerException e) {
			log.info("user does not exists from service");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Does Not exist");
		}
		if (email.equals(userDetails.get().getEmail())) {
			log.info("User Exist from service");
			log.info("mail sending For reset Password......");
			String token= generateToken.generateToken(userDetails.get().getId());
			String link2 = "http://192.168.1.175:8085/user/reset-password/" +token;
			emailGenerate.sendEmail(userDetails.get().getEmail(), "FundooNotes Api Forgot Password Request", link2);
			log.info("mail sent For reset Password......Generated token:"+token);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Exists Enter new Password");
		} else {
			log.info("user does not exists from service");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Does Not exist");
		}
	}

	public ResponseEntity<String> resetPassword(String newPassword, String token) {
		log.info("resetPassword from UserService ");
		
		if (userDAO.updateUserPaword(getUserIdFromToken(token), encryption.passwordEncoder().encode(newPassword)) > 0) {
			log.info("Updated New Password from resetPassword UserService");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Your Password Updataed Successfully");
		} else {
			log.info("Failed to Update New Password from resetPassword UserService");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went to wong please try Again...");
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
