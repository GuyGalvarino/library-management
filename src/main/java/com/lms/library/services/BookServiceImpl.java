package com.lms.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.library.dao.BookDao;
import com.lms.library.dao.UserDao;
import com.lms.library.entities.Book;
import com.lms.library.entities.User;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDao bookDao;
	@Autowired
	private UserDao userDao;

	@Override
	public List<Book> getBooks() {
		return bookDao.findAll();
	}

	@Override
	public Book getBook(Integer bookId) {
		return bookDao.findById(bookId).orElse(null);
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

	@Override
	public Book removeBook(Integer bookId) {
		Book book = bookDao.findById(bookId).orElse(null);
		if (book == null) {
			return null;
		}
		// removing the book from all the users who issued it
		List<User> userList = userDao.findAll();
		for (User user : userList) {
			user.removeBook(bookId);
		}
		userDao.saveAll(userList);
		bookDao.deleteById(bookId);
		return book;
	}
	
	public BookServiceImpl() {}
	public BookServiceImpl(BookDao bookDao, UserDao userDao) {
		this.bookDao=bookDao;
		this.userDao=userDao;
	}
}
