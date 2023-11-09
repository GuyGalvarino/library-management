package com.lms.library.controller;

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

import com.lms.library.entities.User;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorizationService authorizationService;

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

	@PostMapping(path = "/users/login", consumes = "application/json")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
		String email = request.getEmail();
		String password = request.getPassword();
		System.out.println(email);
		System.out.println(password);
		User user = userService.getUserByEmail(email);
		if (user == null) {
			return ResponseEntity.status(404).build();
		}
		if (!user.getPasswordHash().equals(password)) {
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
		User user = userService.createUser(name, email, password);
		if (user == null) {
			return ResponseEntity.status(400).build();
		}
		Integer userId = user.getUserId();
		String token = authorizationService.generateToken(userId);
		return ResponseEntity
				.of(Optional.of(new UserResponseWithToken(user.getUserId(), user.getName(), user.getEmail(), token)));
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId, @RequestHeader String authorization) {
		// TODO token-based authorization
		if (!userId.equals(Integer.parseInt(authorization))) {
			return ResponseEntity.status(403).build();
		}
		User deletedUser = userService.deleteUser(userId);
		return ResponseEntity.of(
				Optional.of(new UserResponse(deletedUser.getUserId(), deletedUser.getName(), deletedUser.getEmail())));
	}
}
