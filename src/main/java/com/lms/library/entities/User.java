package com.lms.library.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "libraryusers")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String name;
	@Column(unique=true)
	private String email;
	private Set<Integer> issuedBooks;
	private String passwordHash;

	public User() {
		super();
		this.issuedBooks = new HashSet<>();
	}

	public User(String name, String email, String passwordHash) {
		super();
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.issuedBooks = new HashSet<>();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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

	public Set<Integer> getIssuedBooks() {
		return issuedBooks;
	}

	public void issueBook(Integer bookId) {
		issuedBooks.add(bookId);
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
