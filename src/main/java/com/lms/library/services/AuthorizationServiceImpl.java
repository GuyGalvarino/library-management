package com.lms.library.services;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
	String secret = "ashodhalksdjlasjdlajsdlkajsdlkajsdlkasjdlkajsldjasldkj";
	Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

	@Override
	public String generateToken(Integer userId) {
		Instant now = Instant.now();
		String jwtToken = Jwts.builder().claim("userId", userId).setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(now)).setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
				.signWith(hmacKey).compact();
		return jwtToken;
	}

	@Override
	public Boolean verifyToken(Integer userId, String token) {
		Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token);
		Integer parsedUserId = jwt.getBody().get("userId", Integer.class);
		return parsedUserId.equals(userId);
	}

	@Override
	public String generateAdminToken(String email) {
		Instant now = Instant.now();
		String jwtToken = Jwts.builder().claim("email", email).setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(now)).setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
				.signWith(hmacKey).compact();
		return jwtToken;
	}

	@Override
	public Boolean verifyAdminToken(String email, String token) {
		Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token);
		String parsedEmail = jwt.getBody().get("email", String.class);
		return parsedEmail.equals(email);
	}

}
