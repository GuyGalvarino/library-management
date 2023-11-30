package com.lms.library.services;

public interface AuthorizationService {
	String generateToken(Integer userId);

	Boolean verifyToken(Integer userId, String token);

	String generateAdminToken(String email);

	Boolean verifyAdminToken(String email, String token);
}
