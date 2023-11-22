package com.lms.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.entities.Admin;
import com.lms.library.services.AdminService;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.MailService;
import com.lms.library.services.OtpService;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AdminController_ {
    @Autowired
    private MailService mailservice;

    @Autowired
    private OtpService otpservice;

    @Autowired
    private AdminService adminservice;

    @Autowired
    private AuthorizationService authorizationservice;

    private static class OtpRequest{
        String name;
        String email;
        String password;

        @SuppressWarnings("unused")
        public String getName() {
            return name;
        }

        @SuppressWarnings("unused")
        public String getEmail() {
            return email;
        }

        @SuppressWarnings("unused")
        public String getPassword() {
            return password;
        }
    }

    private static class OtpResponse_{
        private String message = "Your OTP has been sent to your email check for verification";
        
        @SuppressWarnings("unused")
        public String getMessage(){
            return message;
        }
    }

    private static class TokenRequest{
        String otp;
        String email;
        public String getOtp() {
            return otp;
        }
        public String getEmail() {
            return email;
        }
    }
    private static class TokenResponse{
        String name;
        String token;
        String email;
        public String getName() {
            return name;
        }
        public String getToken() {
            return token;
        }
        public String getEmail() {
            return email;
        }
        public TokenResponse(String name, String token, String email) {
            this.name = name;
            this.token = token;
            this.email = email;
        }
        
    }

    @PostMapping(value="/admin")
    public ResponseEntity<?> requestForOtp(@RequestBody OtpRequest otprequest) {
        String email = otprequest.getEmail();
        String password = otprequest.getPassword();
        Admin admin = adminservice.getAdmin(email);
        if(admin == null){
            return ResponseEntity.status(404).build();
        }
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray() , admin.getPasswordHash());
        if(!result.verified){
            return ResponseEntity.status(403).build();
        }
        otpservice.sendOtpAdmin(admin);
        return ResponseEntity.ok(new OtpResponse_());
    }

    @PostMapping(value="/admin/verify-otp")
    public ResponseEntity<?> validateAdmin(@RequestBody TokenRequest tokenRequest) {
        String otp = tokenRequest.getOtp();
        String email = tokenRequest.getEmail();
        Admin admin = otpservice.verifyOtpAdmin(email, otp);
        if(admin == null){
            return ResponseEntity.status(400).build();
        }
        String token = authorizationservice.generateAdminToken(email);
        return ResponseEntity.ok(new TokenResponse(admin.getName(), token, email));
    }
    
    
    
}
