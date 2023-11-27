package com.lms.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lms.library.dao.UserDao;
import com.lms.library.entities.User;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

	@Test
	public void testCreateUser_SuccessfullSave_ReturnsUser() {
		UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		String name = "Swapnil";
		String email = "Swapnil@gmail.com";
		String passwordHash = "hashedPassword";
		User expectedUser = new User(name, email, passwordHash);
		expectedUser.setUserId(1);
		when(userDao.save(any(User.class))).thenReturn(expectedUser);
		// Act
		User result = userService.createUser(name, email, passwordHash);
		System.out.println(result);
		// Assert
		assertNotNull(result);
		assertEquals(expectedUser, result);
		verify(userDao, times(1)).save(any(User.class));
	}

	@Test
	public void testGetUserByEmail_UserExists_ReturnsUser() {
		// Arrange
		UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		String email = "Swapnil@gmail.com";
		User expectedUser = new User("Swapnil", email, "hashedPassword");
		when(userDao.findByEmail(eq(email))).thenReturn(java.util.Optional.of(expectedUser));

		// Act
		User result = userService.getUserByEmail(email);

		// Assert
		assertNotNull(result);
		assertEquals(expectedUser, result);
		verify(userDao, times(1)).findByEmail(eq(email));
	}

	@Test
	public void testGetUserByEmail_UserDoesNotExists_ReturnsNull() {
		// Arrange
		UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		String email = "noexistent@gmail.com";

		when(userDao.findByEmail(eq(email))).thenReturn(java.util.Optional.empty());

		// Act
		User result = userService.getUserByEmail(email);

		// Assert
		assertNull(result);
		verify(userDao, times(1)).findByEmail(eq(email));
	}

	@Test
	public void testGetUserById_UserExists_ReturnsUser() {
		// Arrange
		UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		Integer userId = 3;
		User expectedUser = new User("Swapnil", "Swapnil@gmail.com", "hashedPassword");
		expectedUser.setUserId(userId);
		when(userDao.findById(eq(userId))).thenReturn(java.util.Optional.of(expectedUser));

		// Act
		User result = userService.getUser(userId);

		// Assert
		assertNotNull(result);
		assertEquals(expectedUser, result);

		verify(userDao, times(1)).findById(eq(userId));
	}

	@Test
	public void testGetUserById_UserDoesNotExists_ReturnsNull() {
		// Arrange
		UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		Integer userId = 3;
		when(userDao.findById(eq(userId))).thenReturn(java.util.Optional.empty());

		// Act
		User result = userService.getUser(userId);

		// Assert
		assertNull(result);

		verify(userDao, times(1)).findById(eq(userId));
	}

	@Test
	public void testDeleteUser_UserExists_DeleteUserandReturnsDeletedUser() {
		// Arrange
		UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		Integer userId = 3;
		User expectedUser = new User("Swapnil", "Swapnil@gmail.com", "hashedPassword");
		expectedUser.setUserId(userId);
		when(userDao.findById(eq(userId))).thenReturn(java.util.Optional.of(expectedUser));

		// Act
		User result = userService.deleteUser(userId);

		// Assert
		assertNotNull(result);
		assertEquals(expectedUser, result);

		verify(userDao, times(1)).findById(eq(userId));
		verify(userDao, times(1)).deleteById(eq(userId));
	}

	@Test
	public void testDeleteUser_UserDoesNotExists_ReturnsNull() {
		// Arrange
		UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		Integer userId = 3;
		when(userDao.findById(eq(userId))).thenReturn(java.util.Optional.empty());

		// Act
		User result = userService.deleteUser(userId);
		// Assert
		assertNull(result);

		verify(userDao, times(1)).findById(eq(userId));
		verify(userDao, times(0)).deleteById(eq(userId));
	}

}
