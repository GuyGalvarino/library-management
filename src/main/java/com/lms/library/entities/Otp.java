package com.lms.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Otp {
    @Id
    private String email;
    private String otp;
    private String passwordHash;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Otp(String email, String name, String passwordHash, String otp) {
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.otp = otp;
    }

    public Otp() {
        super();
    }
}
