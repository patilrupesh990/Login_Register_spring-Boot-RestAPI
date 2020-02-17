package com.bridgelabz.fundooapi.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.bridgelabz.fundooapi.configration.WebMvcConfig;
import com.bridgelabz.fundooapi.dao.IUserDao;
import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.model.UserLoginPair;
import com.bridgelabz.fundooapi.response.LoginResponse;
import com.bridgelabz.fundooapi.response.UserData;
import com.bridgelabz.fundooapi.response.UserResponse;
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
	
	@Autowired
	UserData userresponce;


	@Transactional
	@Override
	public ResponseEntity<UserResponse> registerUser(User user, BindingResult result) {
		log.info("User Registration Service");

		if (userDAO.userExists(user)) {
			log.info("User Alrady exist Service");
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body(new UserResponse(208, "User Is Alrady Registered"));
		}

		else if (result.hasErrors()) {
			log.info("Internal server error in Service");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new UserResponse(208, "Some Internal Error Occurred"));
		} else {
			user.setPassword(encryption.passwordEncoder().encode(user.getPassword()));
			user.setActivate(false);
			user.setCreationTime(DateValidator.getCurrentDate());

			log.info("User Registered");
			userDAO.registerUser(user);
			log.info("mail sending......");
			String link = "http://192.168.1.175:4200/active/" + generateToken.generateToken(user.getId());
			emailGenerate.sendEmail(user.getEmail(), "Foondu Notes Varification", link);
			log.info("MAil sent to user mailId");
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserResponse(208, "Verification Link Sent to your email ===>" + user.getEmail()
							+ "<=== please verify your email first"));
		}
	}

	@Override
	public ResponseEntity<LoginResponse> userLogin(UserLoginPair loginPair) {
		Optional<User> userDetails = Optional.ofNullable(userDAO.getUserByEmail(loginPair.getEmail()));
		log.info("Token Generated======>" + generateToken.generateToken(userDetails.get().getId()));
		if (userDetails.isPresent()) {
			log.info("UserInput password :" + loginPair.getPassword());
			log.info("encrypted :" + userDetails.get().getPassword());

			log.info("password matcher:"
					+ encryption.passwordEncoder().matches(loginPair.getPassword(), userDetails.get().getPassword()));

			if (userDetails.get().getEmail().equals(loginPair.getEmail())
					&& encryption.passwordEncoder().matches(loginPair.getPassword(), userDetails.get().getPassword())) {
				if (userDAO.isUserVerified(loginPair.getEmail())) {
					log.info("Successfully LogedIn...");
					return  ResponseEntity.status(HttpStatus.ACCEPTED).body(new LoginResponse(201,generateToken.generateToken(userDetails.get().getId()), userDetails.get().getFirstname()));
				} else {
					return  ResponseEntity.status(HttpStatus.LOCKED).body(new LoginResponse(423, "invalid username or password",generateToken.generateToken(userDetails.get().getId()) ));
				}
			}
		}
		return  ResponseEntity.status(HttpStatus.LOCKED).body(new LoginResponse(423, "invalid username or password",generateToken.generateToken(userDetails.get().getId())));

	}

	public ResponseEntity<String> activateUserAccount(String token) {
		log.info("Activate USer Service");
		Long id = getUserIdFromToken(token);
		try {
			userDAO.activateUser(id);
			log.info("User verified successfully !");
			return ResponseEntity.accepted()
					.body("<h1 color:'red'>Verification Success!! Congratulations Your Accout Activated</h1><br><h2>+");
		} catch (Exception e) {
			log.info("User with Id: " + id + " does not exist");
			return ResponseEntity.ok().body("<h1 color:'red'>Sorry some internal Error !! Please Try Again</h1>");
		}
	}

	public Long getUserIdFromToken(String token) {
		return (long) generateToken.parseToken(token);
	}

	@Override
	public ResponseEntity<String> verifyBeforeResetPassword(String email) {
		Optional<User> userDetails = Optional.ofNullable(userDAO.getUserByEmail(email));

		try {
			log.info("Fetched user Email from DAO:" + userDetails.get().getEmail());
		} catch (NullPointerException e) {
			log.info("user does not exists from service");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Does Not exist");
		}
		if (email.equals(userDetails.get().getEmail())) {
			log.info("User Exist from service");
			log.info("mail sending For reset Password......");
			String token = generateToken.generateToken(userDetails.get().getId());
			String link2 = "http://192.168.1.175:4200/reset-password/" + token;

			emailGenerate.sendEmail(userDetails.get().getEmail(), "FundooNotes Api Forgot Password Request", link2);
			log.info("mail sent For reset Password......Generated token:" + token);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Exists Enter new Password");
		} else {
			log.error("user does not exists from service");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Does Not exist");
		}
	}

	public ResponseEntity<String> resetPassword(String newPassword, String token) {
		log.info("resetPassword from UserService ");

		if (userDAO.updateUserPaword(getUserIdFromToken(token), encryption.passwordEncoder().encode(newPassword)) > 0) {
			log.info("Updated New Password from resetPassword UserService");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Your Password Updataed Successfully");
		} else {
			log.error("Failed to Update New Password from resetPassword UserService");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went to wong please try Again...");
		}

	}
	@Override
	public ResponseEntity<UserResponse> getUserList() {
		if (userDAO.getAllUser() != null) {
			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new UserResponse(302, "Number OfUsers Are:"+userDAO.getAllUser().size(),userDAO.getAllUser()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new UserResponse(404, "No User User Found or token expired"));
		}
	}
	
	@Override
	public ResponseEntity<UserData> getUserByID(String token)
	{
		if(userDAO.getUserById( getUserIdFromToken(token))!=null)
		{
			User user=userDAO.getUserById(getUserIdFromToken(token));
			BeanUtils.copyProperties(user,userresponce);
			userresponce.setStatus(302);
			userresponce.setResponse("User Data Found");
			return ResponseEntity.status(HttpStatus.FOUND)
					.body(userresponce);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new UserData(404,"User Not Found"));
		}
	}

}
