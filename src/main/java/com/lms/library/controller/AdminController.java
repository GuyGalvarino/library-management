package com.lms.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.entities.Admin;
import com.lms.library.services.AdminService;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.OtpService;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
public class AdminController {
	@Autowired
	private OtpService otpService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AuthorizationService authorizationService;

	private static class OtpRequest {
		private String email;
		private String password;

		public String getEmail() {
			return email;
		}

		public String getPassword() {
			return password;
		}

	}

	private static class TokenRequest {
		private String otp;

		public String getOtp() {
			return otp;
		}
	}

	private static class OtpResponse {
		private String message = "OTP sent to the email of the admin";

		@SuppressWarnings("unused")
		public String getMessage() {
			return message;
		}

	}

	private static class AdminTokenResponse {
		private String token;
		private String email;
		private String name;

		@SuppressWarnings("unused")
		public String getToken() {
			return token;
		}
		@SuppressWarnings("unused")
		public String getEmail() {
			return email;
		}
		
		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		public AdminTokenResponse(String email, String name, String token) {
			this.email = email;
			this.name = name;
			this.token = token;
		}
	}

	@PostMapping("/admin")
	public ResponseEntity<?> getOtp(@RequestBody OtpRequest otpRequest) {
		String email = otpRequest.getEmail();
		String password = otpRequest.getPassword();
		Admin admin = adminService.getAdmin(email);
		if (admin == null) {
			return ResponseEntity.status(404).build();
		}
		BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), admin.getPasswordHash());
		if (!result.verified) {
			return ResponseEntity.status(403).build();
		}
		otpService.sendOtpAdmin(admin);
		return ResponseEntity.ok(new OtpResponse());
	}

	@PostMapping("/admin/verify/{email}")
	public ResponseEntity<?> validateAdminLogin(@RequestBody TokenRequest tokenRequest, @PathVariable String email) {
		String otp = tokenRequest.getOtp();
		Admin admin = otpService.verifyOtpAdmin(email, otp);
		if (admin == null) {
			return ResponseEntity.status(404).build();
		}
		String token = authorizationService.generateAdminToken(email);
		return ResponseEntity.ok(new AdminTokenResponse(admin.getEmail(), admin.getName(), token));
	}

}
