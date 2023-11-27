package com.lms.library.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import com.lms.library.dao.UserDao;
import com.lms.library.entities.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
    public class AuthorizationServiceImplTest {
    private AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl(); 
 
    @Test
    void testGenerateToken() {
    	UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		String name = "Swapnil";
		String email = "Swapnil@gmail.com";
		String passwordHash = "hashedPassword";
		User expectedUser = new User(name, email, passwordHash);
		expectedUser.setUserId(1);
		String token = authorizationService.generateToken(expectedUser.getUserId());
        assertTrue(authorizationService.verifyToken(expectedUser.getUserId(), token));
    }

    @Test
    public void testGenerateAdminToken() {
        String email = "admin9@example.com";
        String token = authorizationService.generateAdminToken(email);
        assertTrue(authorizationService.verifyAdminToken(email, token));
    }
    
    @Test
    public void testVerifyTokenValid() {
    	UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		String name = "Swapnil";
		String email = "Swapnil@gmail.com";
		String passwordHash = "hashedPassword";
		User expectedUser = new User(name, email, passwordHash);
		expectedUser.setUserId(6);
		String token = authorizationService.generateToken(expectedUser.getUserId());
        assertTrue(authorizationService.verifyToken(expectedUser.getUserId(), token));
    }
    
    @Test
    public void testVerifyTokenInvalidUserId() {
    	UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		String name = "Swapnil";
		String email = "Swapnil@gmail.com";
		String passwordHash = "hashedPassword";
		User expectedUser = new User(name, email, passwordHash);
		expectedUser.setUserId(5);
		String token = authorizationService.generateToken(expectedUser.getUserId());
        assertFalse(authorizationService.verifyToken(123, token));
    }

    @Test
    public void testVerifyTokenInvalidToken() {
    	UserDao userDao = mock(UserDao.class);
		UserServiceImpl userService = new UserServiceImpl(userDao);
		String name = "Swapnil";
		String email = "Swapnil@gmail.com";
		String passwordHash = "hashedPassword";
		User expectedUser = new User(name, email, passwordHash);
		expectedUser.setUserId(4);
	    String token = authorizationService.generateToken(5);
      
        assertFalse(authorizationService.verifyToken(expectedUser.getUserId(), token));
    }
    
    @Test
    public void testVerifyAdminTokenValid() {
        String email = "admin3@example.com";
        String token = authorizationService.generateAdminToken(email);
        assertTrue(authorizationService.verifyAdminToken(email, token));
    }

    @Test
    public void testVerifyAdminTokenInvalidEmail() {
        String email = "admin1@example.com";
        String token = authorizationService.generateAdminToken("another_admin@example.com");
        assertFalse(authorizationService.verifyAdminToken(email, token));
    }

    @Test
    public void testVerifyAdminTokenInvalidToken() {
        String email = "admin1@example.com";
        String token =   authorizationService.generateAdminToken("another_admin@example.com");
        assertFalse(authorizationService.verifyAdminToken(email, token));
    }
}
