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
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.entities.Book;
import com.lms.library.entities.User;
import com.lms.library.services.BookService;
import com.lms.library.services.UserService;

@RestController
public class AdminController {
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;

	private static class BookRequest {
		private String name;
		private String author;
		private String publisher;

		public String getName() {
			return name;
		}

		public String getAuthor() {
			return author;
		}

		public String getPublisher() {
			return publisher;
		}

	}

	@PostMapping(path = "/books", consumes = "application/json")
	public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest) {
		String name = bookRequest.getName();
		String author = bookRequest.getAuthor();
		String publisher = bookRequest.getPublisher();
		Book newBook = bookService.addBook(name, author, publisher);
		if (newBook == null) {
			return ResponseEntity.status(400).build();
		}
		return ResponseEntity.of(Optional.of(newBook));
	}

	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<?> removeBook(@PathVariable Integer bookId) {
		Book result = bookService.removeBook(bookId);
		if (result == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.of(Optional.of(result));
	}

	@GetMapping("/users") // temporary for checking the users in the database
	public List<User> getUsers() {
		List<User> userList = userService.getUsers();
		return userList;
	}
}
