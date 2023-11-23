package com.lms.library.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Key;
import java.util.Base64;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceImplTest {
	private static final String secret = "ashodhalksdjlasjdlajsdlkajsdlkajsdlkasjdlkajsldjasldkj";
	Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());


    @InjectMocks
    private AuthorizationServiceImpl authService;
    @Test
    void testGenerateToken() {
    	AuthorizationServiceImpl authServiceImpl = new AuthorizationServiceImpl();
        Integer userId = 1;
        String generatedToken = authServiceImpl.generateToken(userId);

        assertNotNull(generatedToken);
        assertTrue(generatedToken.length() > 0);

        // You may want to verify the behavior of the Jwts.builder() and signWith methods using Mockito
        verify(hmacKey).getAlgorithm();
    }

}
