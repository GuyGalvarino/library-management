package com.lms.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.library.dao.UserDao;
import com.lms.library.entities.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public User createUser(String name, String email, String passwordHash) {
		User newUser = new User(name, email, passwordHash);
		return userDao.save(newUser);
	}

	@Override
	public User getUserByEmail(String email) {
		User user = userDao.findByEmail(email).orElse(null);
		return user;
	}

	@Override
	public User getUser(Integer userId) {
		return userDao.findById(userId).orElse(null);
	}

	@Override
	public List<User> getUsers() {
		return userDao.findAll();
	}

	@Override
	public User deleteUser(Integer userId) {
		User deletedUser = userDao.findById(userId).orElse(null);
		if (deletedUser != null) {
			userDao.deleteById(userId);
		}
		return deletedUser;
	}
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserServiceImpl() {}

}
