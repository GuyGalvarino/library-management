package com.lms.library.entities;

public class Book {
	String bookId;
	String name;
	String author;
	String publisher;

	public Book(String bookId, String name, String author, String publisher) {
		super();
		this.bookId = bookId;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

}
