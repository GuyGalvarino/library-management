package com.lms.library.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.lms.library.dao.AdminDao;
import com.lms.library.dao.OtpDao;
import com.lms.library.entities.Admin;
import com.lms.library.entities.Otp;

public class OtpServiceImplTest {
	
	@Test
	public void testSendOtp() {
		MailService mailService = mock(MailService.class);
		OtpDao otpDao = mock(OtpDao.class);
		OtpServiceImpl otpService = new OtpServiceImpl(otpDao, mailService);
		
		String email="swapnil@gmail.com";
		String name = "Swapnil";
		String password="password";
		//Act
		otpService.sendOtp(email, name, password);
		
		//Assert
		verify(otpDao, times(1)).save(any(Otp.class));
		verify(mailService,times(1)).sendMail(eq(email), anyString(),anyString());
	}
	
	@Test
	public void testSendOtpAdmin() {
		MailService mailService = mock(MailService.class);
		AdminDao adminDao = mock(AdminDao.class);
		OtpDao otpDao = mock(OtpDao.class);
		OtpServiceImpl otpService = new OtpServiceImpl(otpDao,adminDao, mailService);
		
		Admin admin = new Admin();
		admin.setEmail("example@gmail.com");
		
		//Act
		otpService.sendOtpAdmin(admin);
		
		//Assert
		verify(adminDao, times(1)).save(any(Admin.class));
		verify(mailService,times(1)).sendMail(eq(admin.getEmail()), anyString(),anyString());
	}
	
	@Test
	public void testVerifyOtp_CorrectOtp_ReturnOtp() {
		MailService mailService = mock(MailService.class);
		OtpDao otpDao = mock(OtpDao.class);
		OtpServiceImpl otpService = new OtpServiceImpl(otpDao, mailService);
		
		String email="swapnil@gmail.com";
		String otp = "123456";
		Otp userOtp = new Otp(email,"Swapnil","hashedPassword",otp);
		when(otpDao.findById(eq(email))).thenReturn(java.util.Optional.of(userOtp));
		//Act
		Otp result = otpService.verifyOtp(email, otp);
		
		//Assert
		assertNotNull(result);
		assertEquals(userOtp,result);
		
		//verify
		verify(otpDao, times(1)).findById(eq(email));
		verify(otpDao,times(1)).deleteById(eq(email));
	}
	
	@Test
	public void testVerifyOtp_IncorrectOtp_ReturnsNUll() {
		MailService mailService = mock(MailService.class);
		OtpDao otpDao = mock(OtpDao.class);
		OtpServiceImpl otpService = new OtpServiceImpl(otpDao, mailService);
		
		String email="swapnil@gmail.com";
		String otp = "123456";
		Otp userOtp = new Otp(email,"Swapnil","hashedPassword","654321");
		when(otpDao.findById(eq(email))).thenReturn(java.util.Optional.of(userOtp));
		//Act
		Otp result = otpService.verifyOtp(email, otp);
		
		//Assert
		assertNull(result);
		
		//verify
		verify(otpDao, times(1)).findById(eq(email));
		verify(otpDao,times(0)).deleteById(eq(email));
	}
	
	@Test
	public void testVerifyOtp_OtpNotFound_ReturnsNUll() {
		MailService mailService = mock(MailService.class);
		OtpDao otpDao = mock(OtpDao.class);
		OtpServiceImpl otpService = new OtpServiceImpl(otpDao, mailService);
		
		String email="swapnil@gmail.com";
		String otp = "123456";
		when(otpDao.findById(eq(email))).thenReturn(java.util.Optional.empty());
		//Act
		Otp result = otpService.verifyOtp(email, otp);
		
		//Assert
		assertNull(result);
		
		//verify
		verify(otpDao, times(1)).findById(eq(email));
		verify(otpDao,times(0)).deleteById(eq(email));
	}
	
	@Test
	public void testVerifyOtpAdmin_CorrectOtp_ReturnsAdmin() {
		MailService mailService = mock(MailService.class);
		AdminDao adminDao = mock(AdminDao.class);
		OtpDao otpDao = mock(OtpDao.class);
		OtpServiceImpl otpService = new OtpServiceImpl(otpDao,adminDao, mailService);
		
		String email="swapnil@gmail.com";
		String otp = "123456";
		Admin admin = new Admin();
		admin.setEmail(email);
		admin.setOtp(otp);
	
		when(adminDao.findById(eq(email))).thenReturn(java.util.Optional.of(admin));
		//Act
		Admin result = otpService.verifyOtpAdmin(email, otp);
		
		//Assert
		assertNotNull(result);
		assertNull(result.getOtp());
		
		//verify
		verify(adminDao, times(1)).findById(eq(email));
		verify(adminDao,times(1)).save(any(Admin.class));
	}
	
	@Test
	public void testVerifyOtpAdmin_IncorrectOtp_ReturnsNull() {
		MailService mailService = mock(MailService.class);
		AdminDao adminDao = mock(AdminDao.class);
		OtpDao otpDao = mock(OtpDao.class);
		OtpServiceImpl otpService = new OtpServiceImpl(otpDao,adminDao, mailService);
		
		String email="swapnil@gmail.com";
		String otp = "123456";
		Admin admin = new Admin();
		admin.setEmail(email);
		admin.setOtp("654321");
	
		when(adminDao.findById(eq(email))).thenReturn(java.util.Optional.of(admin));
		//Act
		Admin result = otpService.verifyOtpAdmin(email, otp);
		
		//Assert
		assertNull(result);
		
		//verify
		verify(adminDao, times(1)).findById(eq(email));
		verify(adminDao,times(0)).save(any(Admin.class));
	}
	
	@Test
	public void testVerifyOtpAdmin_AdminNotFound_ReturnsNull() {
		MailService mailService = mock(MailService.class);
		AdminDao adminDao = mock(AdminDao.class);
		OtpDao otpDao = mock(OtpDao.class);
		OtpServiceImpl otpService = new OtpServiceImpl(otpDao,adminDao, mailService);
		
		String email="swapnil@gmail.com";
		String otp = "123456";
	
		when(adminDao.findById(eq(email))).thenReturn(java.util.Optional.empty());
		//Act
		Admin result = otpService.verifyOtpAdmin(email, otp);
		
		//Assert
		assertNull(result);
		
		//verify
		verify(adminDao, times(1)).findById(eq(email));
		verify(adminDao,times(0)).save(any(Admin.class));
	}
}
