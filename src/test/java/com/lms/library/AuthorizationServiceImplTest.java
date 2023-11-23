package com.lms.library;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Key;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import com.lms.library.services.AuthorizationServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthorizationServiceImplTest {
	private static final String SECRET = "ashodhalksdjlasjdlajsdlkajsdlkajsdlkasjdlkajsldjasldkj";
    
	@InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(authorizationService, "secret", SECRET);
    }
    
    @Mock
    private Key hmacKey;

    @Test
    public void testGenerateToken() {
        Integer userId = 1;
        String token = authorizationService.generateToken(userId);

        assertNotNull(token);
        // You may want to use an appropriate library to decode the token and assert its content
    }

    @Test
    public void testVerifyToken() {
        Integer userId = 1;
        String token = Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 minutes
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();

        assertTrue(authorizationService.verifyToken(userId, token));
    }

    @Test
    public void testGenerateAdminToken() {
        String email = "admin@example.com";
        String token = authorizationService.generateAdminToken(email);

        assertNotNull(token);
        // You may want to use an appropriate library to decode the token and assert its content
    }

    @Test
    public void testVerifyAdminToken() {
        String email = "admin@example.com";
        String token = Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 minutes
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();

        assertTrue(authorizationService.verifyAdminToken(email, token));
    }
}

