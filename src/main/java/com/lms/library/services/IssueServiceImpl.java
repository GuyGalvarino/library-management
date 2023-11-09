package com.lms.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.library.dao.UserDao;
import com.lms.library.entities.Book;
import com.lms.library.entities.User;

@Service
public class IssueServiceImpl implements IssueService {
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;

	@Override
	public List<Book> getIssues(Integer userId) {
		User user = userDao.findById(userId).orElse(null);
		if (user != null) {
			return populateBooks(user.getIssuedBooks());
		}
		return null;
	}

	// method to populate the books list from their IDs
	List<Book> populateBooks(Set<Integer> bookIds) {
		List<Book> issuedBooks = new ArrayList<>();
		for (Integer bookId : bookIds) {
			issuedBooks.add(bookService.getBook(bookId));
		}
		return issuedBooks;
	}

	@Override
	public Book addIssue(Integer bookId, Integer userId) {
		Book fetchedBook = bookService.getBook(bookId);
		if (fetchedBook == null) {
			return null;
		}
		User fetchedUser = userService.getUser(userId);
		if (fetchedUser == null) {
			return null;
		}
		fetchedUser.issueBook(bookId);
		userDao.save(fetchedUser);
		return fetchedBook;
	}

	@Override
	public Book removeIssue(Integer bookId, Integer userId) {
		User fetchedUser = userService.getUser(userId);
		if (fetchedUser == null) {
			return null;
		}
		Integer result = fetchedUser.removeBook(bookId);
		userDao.save(fetchedUser);
		return result == -1 ? null : bookService.getBook(bookId);
	}
}
