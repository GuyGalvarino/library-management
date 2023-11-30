package com.lms.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Admin {
  @Id
  private String email;
  private String name;
  private String passwordHash;
  private String otp;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  public Admin(String email, String name, String passwordHash) {
    this.email = email;
    this.name = name;
    this.passwordHash = passwordHash;
    this.otp = null;
  }

  public Admin() {
    super();
  }
}
