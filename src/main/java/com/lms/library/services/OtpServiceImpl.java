package com.lms.library.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.library.dao.AdminDao;
import com.lms.library.dao.OtpDao;
import com.lms.library.dao.UserDao;
import com.lms.library.entities.Admin;
import com.lms.library.entities.Otp;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    MailService mailService;
    @Autowired
    OtpDao otpDao;
    @Autowired
    AdminDao adminDao;

    @Override
    public void sendOtp(String email, String name, String password) {
        String otp = Integer.toString(new Random().nextInt(1000000));
        while (otp.length() < 6) {
            otp = "0" + otp;
        }
        String passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        otpDao.save(new Otp(email, name, passwordHash, otp));
        // System.out.println(otp);    // for testing frontend
        mailService.sendMail(email, "LMS user verification", "Your OTP is " + otp);
    }

    @Override
    public void sendOtpAdmin(Admin admin) {
        String otp = Integer.toString(new Random().nextInt(1000000));
        while (otp.length() < 6) {
            otp = "0" + otp;
        }
        admin.setOtp(otp);
        adminDao.save(admin);
        // System.out.println(otp);    // for testing frontend
        mailService.sendMail(admin.getEmail(), "LMS admin verification", "Your OTP is " + otp);
    }

    @Override
    public Otp verifyOtp(String email, String otp) {
        Otp userOtp = otpDao.findById(email).orElse(null);
        if (userOtp == null) {
            return null;
        }
        if (userOtp.getOtp().equals(otp)) {
            otpDao.deleteById(email);
            return userOtp;
        }
        return null;
    }

    @Override
    public Admin verifyOtpAdmin(String email, String otp) {
        Admin admin = adminDao.findById(email).orElse(null);
        if (admin == null) {
            return null;
        }
        if (admin.getOtp().equals(otp)) {
            admin.setOtp(null);
            adminDao.save(admin);
            return admin;
        }
        return null;
    }
    
    public OtpServiceImpl() {}
    
	public OtpServiceImpl(OtpDao otpDao, MailService mailService) {
		this.otpDao = otpDao;
		this.mailService = mailService;
	} 
	
	public OtpServiceImpl(OtpDao otpDao, AdminDao adminDao, MailService mailService) {
		this.adminDao=adminDao;
		this.otpDao=otpDao;
		this.mailService = mailService;
	}

}
