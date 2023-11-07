package com.lms.library.services;

import java.util.List;

import com.lms.library.entities.User;

public interface UserService {
	User getUser(String email);
	User createUser(String name, String email, String password);
	List<User> getUsers();
}
