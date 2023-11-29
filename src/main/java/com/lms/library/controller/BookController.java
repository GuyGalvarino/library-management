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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lms.library.entities.Book;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.BookService;

@RestController
public class BookController {
	@Autowired
	public BookService bookService;
	@Autowired
	public AuthorizationService authorizationService;

	public static class BookRequest {
		private String name;
		private String author;
		private String publisher;
		private String adminEmail;

		public String getAdminEmail() {
			return adminEmail;
		}

		public String getName() {
			return name;
		}

		public String getAuthor() {
			return author;
		}

		public String getPublisher() {
			return publisher;
		}

		public BookRequest(String name, String author, String publisher, String adminEmail) {
			super();
			this.name = name;
			this.author = author;
			this.publisher = publisher;
			this.adminEmail = adminEmail;
		}

	}

	public static class BookDeleteRequest {
		private String adminEmail;

		public String getAdminEmail() {
			return adminEmail;
		}

		@JsonCreator
		public BookDeleteRequest(@JsonProperty("adminEmail") String adminEmail) {
			super();
			this.adminEmail = adminEmail;
		}
	}

	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return bookService.getBooks();
	}

	@GetMapping("/books/{bookId}")
	public ResponseEntity<?> getBook(@PathVariable Integer bookId) {
		Book result = bookService.getBook(bookId);
		if (result == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.of(Optional.of(result));
	}

	@PostMapping(path = "/books", consumes = "application/json")
	public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest, @RequestHeader String authorization) {
		String name = bookRequest.getName();
		String author = bookRequest.getAuthor();
		String publisher = bookRequest.getPublisher();
		String adminEmail = bookRequest.getAdminEmail();
		if (!authorizationService.verifyAdminToken(adminEmail, authorization)) {
			return ResponseEntity.status(403).build();
		}
		Book newBook = bookService.addBook(name, author, publisher);
		if (newBook == null) {
			return ResponseEntity.status(400).build();
		}
		return ResponseEntity.ok(newBook);
	}

	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<?> removeBook(@PathVariable Integer bookId, @RequestBody BookDeleteRequest bookDeleteRequest,
			@RequestHeader String authorization) {
		String adminEmail = bookDeleteRequest.getAdminEmail();
		if (!authorizationService.verifyAdminToken(adminEmail, authorization)) {
			return ResponseEntity.status(403).build();
		}
		Book result = bookService.removeBook(bookId);
		if (result == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.of(Optional.of(result));
	}
}
