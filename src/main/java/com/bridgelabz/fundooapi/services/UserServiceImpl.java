package com.bridgelabz.fundooapi.services;

package com.bridgelabz.fundooapi.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.util.EmailGenerator;
import com.bridgelabz.fundooapi.repository.UserRepository;
import com.bridgelabz.fundooapi.util.JwtTokenUtil;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenUtil generateToken;

	@Autowired
	private EmailGenerator emailGenerate;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public ResponseEntity<String> registerUser(User user, BindingResult result) {




		if (userRepository.findAll().stream()
				.filter(userprocess -> userprocess.getEmail().equals(user.getEmail())) != null) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("User Is Alrady Registered");
		}

		else if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.LOOP_DETECTED).body("Some Internal Error Occurred");

		else {

			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setActivate("Not varified");
			user.setRegistration_date(LocalDate.now());
			String link = "http://localhost:8082/register/activateuser" + generateToken.generateToken(user.getId());
			emailGenerate.sendEmail(user.getEmail(), "Foondu Notes Varification", link);
			userRepository.save(user);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Verification Link Sent to your email" + user.getEmail() + " please verify your email first");

		}

	}

	public List<User> getUserList() {
		return userRepository.findAll();
	}

	@Override
	public User findUserByUserName(String userName) {
		return userRepository.findByEmail(userName);
	}

}
