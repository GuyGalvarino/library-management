package com.lms.library.services;

import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    private final String from = "jakartafrom@example.com";
    private final String username = "a48eb605fc5ce2";
    private final String password = "6fc7c182c4de74";
    private final String host = "sandbox.smtp.mailtrap.io";
    private final Integer port = 587;
    @Override
    public Boolean sendMail(String email, String subject, String body) {
        // provide recipient's email ID
        String to = email;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        // create the Session object
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, authenticator);
        try {
            // create a MimeMessage object
            Message message = new MimeMessage(session);
            // set From email field
            message.setFrom(new InternetAddress(from));
            // set To email field
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // set email subject field
            message.setSubject(subject);
            // set the content of the email message
            message.setContent(body, "text/html");
            // send the email message
            Transport.send(message);
            System.out.println("email sent successfully");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
