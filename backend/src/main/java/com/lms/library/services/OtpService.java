package com.lms.library.services;

import com.lms.library.entities.Admin;
import com.lms.library.entities.Otp;

public interface OtpService {
    public void sendOtp(String email, String name, String password);

    public void sendOtpAdmin(Admin admin);

    public Otp verifyOtp(String email, String otp);

    public Admin verifyOtpAdmin(String email, String otp);

}
