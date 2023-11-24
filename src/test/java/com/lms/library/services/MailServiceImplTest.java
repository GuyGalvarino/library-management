package com.lms.library.services;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class MailServiceImplTest {

    @Test
    public void testSendMail() throws Exception {
    	MailServiceImpl mailService = new MailServiceImpl();
        // Call the sendMail method
        boolean result = mailService.sendMail("recipient@example.com", "Test Subject", "Test Body");
        // Verify that the email was sent successfully
        assertTrue(result);
    }
}