package com.lms.library.services;

import com.lms.library.entities.Otp;

public interface OtpService {
    public void sendOtp(String email, String name, String password);
    public Otp verifyOtp(String email, String otp);
}
