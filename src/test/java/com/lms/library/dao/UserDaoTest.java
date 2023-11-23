package com.lms.library.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lms.library.entities.User;

@DataJpaTest
public class UserDaoTest {
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testFindByEmail_UserExists_ReturnsUser() {
		//Arrange
		String email = "Swapnil@gmail.com";
		User user = new User("Swapnil",email,"hashedPassword");
		userDao.save(user);
		
		//Act
		java.util.Optional<User> result = userDao.findByEmail(email);
		
		//Assert
		assertTrue(result.isPresent());
		assertEquals(user, result.get());
		
	}
	
	@Test
	public void testFindByEmail_UserDoesNotExists_ReturnsEmptyOptional() {
		//Arrange
		String email = "nonexistent@gmail.com";
		
		//Act
		java.util.Optional<User> result = userDao.findByEmail(email);
		
		//Assert
		assertFalse(result.isPresent());	
	}
}
