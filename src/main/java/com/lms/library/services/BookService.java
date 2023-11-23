package com.lms.library.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.library.entities.Book;

@Service
public interface BookService {
	List<Book> getBooks();
	Book getBook(Integer bookId);
	Book addBook(String name, String author, String publisher);
	Book removeBook(Integer bookId);
}
