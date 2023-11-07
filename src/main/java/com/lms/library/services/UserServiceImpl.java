package com.lms.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.library.entities.User;

@Service
public class UserServiceImpl implements UserService {
	private List<User> userList;
	private static Integer counter = 0; // temporary, for generating unique userIDs

	UserServiceImpl() {
		// adding some dummy users for testing
		userList = new ArrayList<>();
		User user1 = new User("u123", "Suresh", "suresh@xyz.com", "aslhdalkjdslkjsdal");
		user1.issueBook("a1234");
		user1.issueBook("a1236");
		User user2 = new User("u124", "Mukesh", "mukesh@yzz.com", "asljdhalkjsdlajsdl");
		user2.issueBook("a1235");
		User user3 = new User("u125", "Swapnil", "swapnil@yrl.com", "zxnkcbklsaodjlkafg");
		user3.issueBook("a1237");
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
	}

	@Override
	public User getUser(String email) {
		for (User user : userList) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public User createUser(String name, String email, String password) {
		if(getUser(email) == null) {
			String userId = "u" + counter.toString();
			counter++;
			User newUser = new User(userId, name, email, password);	
			userList.add(newUser);
			return newUser;
		}
		return null;
	}

	@Override
	public List<User> getUsers() {
		return userList;
	}
	
	

}
