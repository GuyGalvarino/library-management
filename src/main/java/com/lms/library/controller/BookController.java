package com.lms.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.entities.Book;
import com.lms.library.services.BookService;

@RestController
public class BookController {
	@Autowired
	public BookService bookService;

	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return bookService.getBooks();
	}

	@GetMapping("/books/{bookId}")
	public ResponseEntity<?> getBook(@PathVariable String bookId) {
		Book result = bookService.getBook(bookId);
		if (result == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.of(Optional.of(result));
	}
}
