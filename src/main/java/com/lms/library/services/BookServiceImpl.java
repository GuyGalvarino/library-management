package com.lms.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.library.dao.BookDao;
import com.lms.library.entities.Book;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDao bookDao;

	@Override
	public List<Book> getBooks() {
		return bookDao.findAll();
	}

	@Override
	public Book getBook(Integer bookId) {
		try {
			return bookDao.getReferenceById(bookId);
		} catch (EntityNotFoundException e) {
			System.out.println("Book does not exist");
		}
		return null;
	}

	@Override
	public Book addBook(String name, String author, String publisher) {
		try {
			return bookDao.save(new Book(name, author, publisher));
		} catch (Exception e) {
			System.out.println("Could not add book, something went wrong...");
			e.printStackTrace();
		}
		return null;
	}

}
