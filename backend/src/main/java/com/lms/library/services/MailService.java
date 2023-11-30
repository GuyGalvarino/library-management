package com.lms.library.services;

public interface MailService {
    public Boolean sendMail(String email, String subject, String body);    
}
