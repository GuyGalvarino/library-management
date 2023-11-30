package com.lms.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.library.entities.Otp;

public interface OtpDao extends JpaRepository<Otp, String> {
}
