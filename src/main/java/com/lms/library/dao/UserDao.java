package com.lms.library.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.library.entities.User;

public interface UserDao extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}
