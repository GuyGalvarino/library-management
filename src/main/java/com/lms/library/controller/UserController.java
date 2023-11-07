package com.lms.library.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.entities.Book;
import com.lms.library.entities.User;
import com.lms.library.services.BookService;
import com.lms.library.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;

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
		private String userId;
		private String name;
		private String email;
		private String token;
		private List<Book> issuedBooks;

		@SuppressWarnings("unused")
		public String getUserId() {
			return userId;
		}

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		public List<Book> getIssuedBooks() {
			return issuedBooks;
		}

		@SuppressWarnings("unused")
		public String getToken() {
			return token;
		}

		@SuppressWarnings("unused")
		public String getEmail() {
			return email;
		}

		UserResponse(String userId, String name, String email, List<Book> issuedBooks, String token) {
			super();
			this.userId = userId;
			this.name = name;
			this.email = email;
			this.issuedBooks = issuedBooks;
			this.token = token;
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

	@GetMapping("/users") // temporary for checking the users in the database
	public List<User> getUsers() {
		List<User> userList = userService.getUsers();
		return userList;
	}
	
	@GetMapping("/users/{email}")
	public ResponseEntity<?> getUser(@PathVariable String email, @RequestHeader String authorization) {
		if (!email.equals(authorization)) {
			return ResponseEntity.status(403).build();
		}
		User user = userService.getUser(email);
		if (user == null) {
			return ResponseEntity.status(404).build();
		}
		// populating the books
		List<Book> issuedBooks = populateBooks(user.getIssuedBooks());
		String token = authorization;
		return ResponseEntity
				.of(Optional.of(new UserResponse(user.getUserId(), user.getName(), user.getEmail(), issuedBooks, token)));
	}

	@PostMapping(path = "/users/login", consumes = "application/json")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
		String email = request.getEmail();
		String password = request.getPassword();
		User user = userService.getUser(email);
		if (user == null) {
			return ResponseEntity.status(404).build();
		}
		if (!user.getPasswordHash().equals(password)) {
			return ResponseEntity.status(403).build();
		}
		// populating the books
		List<Book> issuedBooks = populateBooks(user.getIssuedBooks());
		String token = user.getUserId();
		return ResponseEntity.of(
				Optional.of(new UserResponse(user.getUserId(), user.getName(), user.getEmail(), issuedBooks, token)));
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
		List<Book> issuedBooks = populateBooks(user.getIssuedBooks());
		String token = user.getUserId();
		return ResponseEntity.of(
				Optional.of(new UserResponse(user.getUserId(), user.getName(), user.getEmail(), issuedBooks, token)));
	}

	// method to populate the books list from their IDs
	List<Book> populateBooks(Set<String> bookIds) {
		List<Book> issuedBooks = new ArrayList<>();
		for (String bookId : bookIds) {
			issuedBooks.add(bookService.getBook(bookId));
		}
		return issuedBooks;
	}
}
