package com.lms.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.SequenceGenerator;

@Entity
public class Book {
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE)
	Integer bookId;
	String name;
	String author;
	String publisher;
	
	public Book() {
		super();
	}

	public Book(String name, String author, String publisher) {
		super();
		this.name = name;
		this.author = author;
		this.publisher = publisher;
	}
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
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

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", name=" + name + ", author=" + author + ", publisher=" + publisher + "]";
	}

}
