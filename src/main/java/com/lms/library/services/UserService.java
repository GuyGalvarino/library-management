package com.lms.library.services;

import java.util.List;

import com.lms.library.entities.User;

public interface UserService {
	User getUserByEmail(String email);

	User getUser(Integer userId);

	User createUser(String name, String email, String password);

	User deleteUser(Integer userId);

	List<User> getUsers();
}
