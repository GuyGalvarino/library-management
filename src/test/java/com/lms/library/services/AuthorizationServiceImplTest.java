package com.lms.library.services;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lms.library.entities.User;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Key;
import java.util.Base64;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceImplTest {
	@Mock
    private AuthorizationService authorizationService;

    @Before
    public void setUp() {
        authorizationService = new AuthorizationServiceImpl(); 
    }
    
    @Test
    void testGenerateToken() {
        Integer userId = 1;
        User mockUser = new User();
        mockUser.setUserId(userId);
        String token = authorizationService.generateToken(userId);
        assertTrue(authorizationService.verifyToken(userId, token));
    }

    @Test
    public void testGenerateAdminToken() {
        String email = "admin@example.com";
        String token = authorizationService.generateAdminToken(email);
        assertTrue(authorizationService.verifyAdminToken(email, token));
    }
    
    @Test
    public void testVerifyTokenValid() {
        Integer userId = 456;
        User mockUser = new User();
        mockUser.setUserId(userId);
        String token = authorizationService.generateToken(userId);
        assertTrue(authorizationService.verifyToken(userId, token));
    }
    
    @Test
    public void testVerifyTokenInvalidUserId() {
        Integer userId = 789;
        User mockUser = new User();
        mockUser.setUserId(userId);
        String token = authorizationService.generateToken(123);
        assertFalse(authorizationService.verifyToken(userId, token));
    }

    @Test
    public void testVerifyTokenInvalidToken() {
        Integer userId = 123;
        User mockUser = new User();
        mockUser.setUserId(userId);
        String token = "invalid_token";
        assertFalse(authorizationService.verifyToken(userId, token));
    }
    
    @Test
    public void testVerifyAdminTokenValid() {
        String email = "admin@example.com";
        String token = authorizationService.generateAdminToken(email);
        assertTrue(authorizationService.verifyAdminToken(email, token));
    }

    @Test
    public void testVerifyAdminTokenInvalidEmail() {
        String email = "admin@example.com";
        String token = authorizationService.generateAdminToken("another_admin@example.com");
        assertFalse(authorizationService.verifyAdminToken(email, token));
    }

    @Test
    public void testVerifyAdminTokenInvalidToken() {
        String email = "admin@example.com";
        String token = "invalid_token";
        assertFalse(authorizationService.verifyAdminToken(email, token));
    }
}
