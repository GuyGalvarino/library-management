package com.lms.library.services;

import java.util.List;

import com.lms.library.entities.Book;

public interface IssueService {

	List<Book> getIssues(Integer userId);

}
