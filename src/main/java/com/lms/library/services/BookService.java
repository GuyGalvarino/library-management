package com.lms.library.services;

import java.util.List;

import com.lms.library.entities.Book;

public interface BookService {
	List<Book> getBooks();
	Book getBook(Integer bookId);
	Book addBook(String name, String author, String publisher);
}
