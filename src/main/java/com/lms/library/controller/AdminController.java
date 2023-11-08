package com.lms.library.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.entities.Book;
import com.lms.library.services.BookService;

@RestController
public class AdminController {
	@Autowired
	private BookService bookService;
	
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
}
