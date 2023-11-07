package com.lms.library.entities;

import java.util.HashSet;
import java.util.Set;

public class User {
	private String userId;
	private String name;
	private String email;
	private Set<String> issuedBooks;
	private String passwordHash;

	public User(String userId, String name, String email, String passwordHash) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.issuedBooks = new HashSet<>();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getIssuedBooks() {
		return issuedBooks;
	}

	public void issueBook(String bookId) {
		issuedBooks.add(bookId);
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
