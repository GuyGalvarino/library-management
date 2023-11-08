package com.lms.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lms.library.dao.UserDao;
import com.lms.library.entities.User;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public User createUser(String name, String email, String password) {
		User newUser = new User(name, email, password);
		userDao.save(newUser);
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.findOne(Example.of(new User("", email, ""))).orElse(null);
	}

	@Override
	public User getUser(Integer userId) {
		try {
			return userDao.getReferenceById(userId);
		} catch (EntityNotFoundException e) {
			System.out.println("User does not exist");
		}
		return null;
	}

	@Override
	public List<User> getUsers() {
		return userDao.findAll();
	}

	@Override
	public User deleteUser(Integer userId) {
		try {
			User deletedUser = userDao.getReferenceById(userId);
			userDao.deleteById(userId);
			return deletedUser;
		} catch (EntityNotFoundException e) {
			System.out.println("User does not exist");
		}
		return null;
	}

}
