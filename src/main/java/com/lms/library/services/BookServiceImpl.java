package com.lms.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.library.entities.Book;

@Service
public class BookServiceImpl implements BookService {

	List<Book> bookList;

	BookServiceImpl() {
		// adding some dummy books for testing
		bookList = new ArrayList<>();
		bookList.add(new Book("a1234", "Core Java", "Mohan", "Sonal"));
		bookList.add(new Book("a1235", "Spring Framework", "Sonam", "Sonal"));
		bookList.add(new Book("a1236", "C++ Basics", "Aryan", "Pragati"));
		bookList.add(new Book("a1237", "Qt Framework", "Riya", "Sonal"));
	}

	@Override
	public List<Book> getBooks() {
		// TODO fetch from database
		return bookList;
	}

	@Override
	public Book getBook(String bookId) {
		// TODO fetch from database
		for (Book b : bookList) {
			if (b.getBookId().equals(bookId)) {
				return b;
			}
		}
		return null;
	}

}
