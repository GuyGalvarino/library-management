package com.lms.library.services;

import java.util.List;

import com.lms.library.entities.Book;

public interface IssueService {

	List<Book> getIssues(Integer userId);
	Book addIssue(Integer bookId, Integer userId);
	Book removeIssue(Integer bookId, Integer userId);
}
