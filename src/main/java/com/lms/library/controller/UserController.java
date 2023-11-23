package com.lms.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.entities.Otp;
import com.lms.library.entities.User;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.OtpService;
import com.lms.library.services.UserService;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorizationService authorizationService;
	@Autowired
	private OtpService otpService;

	private static class LoginRequest {
		String email;
		String password;

		public String getEmail() {
			return email;
		}

		public String getPassword() {
			return password;
		}
	}

	private static class UserResponse {
		private Integer userId;
		private String name;
		private String email;

		@SuppressWarnings("unused")
		public Integer getUserId() {
			return userId;
		}

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		public String getEmail() {
			return email;
		}

		UserResponse(Integer userId, String name, String email) {
			super();
			this.userId = userId;
			this.name = name;
			this.email = email;
		}
	}

	private static class UserResponseWithToken extends UserResponse {
		private String token;

		public UserResponseWithToken(Integer userId, String name, String email, String token) {
			super(userId, name, email);
			this.token = token;
		}

		@SuppressWarnings("unused")
		public String getToken() {
			return token;
		}
	}

	private static class SigninRequest {
		String name;
		String password;
		String email;

		public String getName() {
			return name;
		}

		public String getPassword() {
			return password;
		}

		public String getEmail() {
			return email;
		}
	}

	private static class VerifyRequest {
		String otp;
		String email;

		public String getOtp() {
			return otp;
		}
		public String getEmail() {
			return email;
		}
	}

	public static class SigninUnverifiedResponse {
		String message = "A OTP has been sent to your email";

		public String getMessage() {
			return message;
		}
	}

	@GetMapping("/users/{email}")
	public ResponseEntity<?> getUser(@PathVariable String email, @RequestHeader String authorization) {
		User user = userService.getUserByEmail(email);
		if (user == null) {
			return ResponseEntity.status(404).build();
		}
		Integer userId = user.getUserId();
		if (!authorizationService.verifyToken(userId, authorization)) {
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.of(Optional.of(new UserResponse(user.getUserId(), user.getName(), user.getEmail())));
	}

	@GetMapping("/users") // temporary for checking the users in the database
	public List<User> getUsers() {
		List<User> userList = userService.getUsers();
		return userList;
	}

	@PostMapping(path = "/users/login", consumes = "application/json")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
		String email = request.getEmail();
		String password = request.getPassword();
		User user = userService.getUserByEmail(email);
		if (user == null) {
			return ResponseEntity.status(404).build();
		}
		BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPasswordHash());
		if (!result.verified) {
			return ResponseEntity.status(403).build();
		}
		Integer userId = user.getUserId();
		String token = authorizationService.generateToken(userId);
		return ResponseEntity
				.of(Optional.of(new UserResponseWithToken(user.getUserId(), user.getName(), user.getEmail(), token)));
	}

	@PostMapping(path = "/users/signin", consumes = "application/json")
	public ResponseEntity<?> signinUser(@RequestBody SigninRequest request) {
		String name = request.getName();
		String email = request.getEmail();
		String password = request.getPassword();

		if (userService.getUserByEmail(email) != null) {
			return ResponseEntity.status(403).build();
		}

		otpService.sendOtp(email, name, password);
		return ResponseEntity.of(Optional.of(new SigninUnverifiedResponse()));
	}

	@PostMapping(path = "/users/verify-otp", consumes = "application/json")
	public ResponseEntity<?> verifyUser(@RequestBody VerifyRequest verifyRequest) {
		    String otp = verifyRequest.getOtp();
		    String email = verifyRequest.getEmail();
		    Otp userOtp = otpService.verifyOtp(email, otp);

		    if (userOtp == null) {
		        return ResponseEntity.status(403).build();
		    }

		    // Check if the user already exists
		    User existingUser = userService.getUserByEmail(email);
		    if (existingUser != null) {
		        return ResponseEntity.status(400).build(); // User already verified
		    }

		    // Create the user
		    User user = userService.createUser(userOtp.getName(), userOtp.getEmail(), userOtp.getPasswordHash());

		    // Check if the user creation was successful
		    if (user == null) {
		        return ResponseEntity.status(500).build(); // Server error
		    }

		    Integer userId = user.getUserId();
		    String token = authorizationService.generateToken(userId);

		    return ResponseEntity
		            .of(Optional.of(new UserResponseWithToken(user.getUserId(), user.getName(), user.getEmail(), token)));
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId, @RequestHeader String authorization) {
		if (!authorizationService.verifyToken(userId, authorization)) {
			return ResponseEntity.status(403).build();
		}
		User deletedUser = userService.deleteUser(userId);
		return ResponseEntity.of(
				Optional.of(new UserResponse(deletedUser.getUserId(), deletedUser.getName(), deletedUser.getEmail())));
	}
}
