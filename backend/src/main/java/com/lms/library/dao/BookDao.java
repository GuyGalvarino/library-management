package com.lms.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.library.entities.Book;

public interface BookDao extends JpaRepository<Book, Integer> {}
