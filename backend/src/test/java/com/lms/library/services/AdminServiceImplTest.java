package com.lms.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lms.library.dao.AdminDao;
import com.lms.library.entities.Admin;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AdminServiceImplTest {
	@Test
	public void testGetAdminByEmail_AdminExists_ReturnsAdmin() {
		AdminDao adminDao = mock(AdminDao.class);
		AdminServiceImpl adminService = new AdminServiceImpl(adminDao);
		String email = "Swapnil@gmail.com";
		Admin expectedAdmin = new Admin(email,"Swapnil", "hashedPassword");
		when(adminDao.findById(eq(email))).thenReturn(java.util.Optional.of(expectedAdmin));

		Admin result = adminService.getAdmin(email);

		assertNotNull(result);
		assertEquals(expectedAdmin, result);
		verify(adminDao, times(1)).findById(eq(email));
	}

	@Test
	public void testGetAdminByEmail_AdminDoesNotExists_ReturnsNull() {
		AdminDao adminDao = mock(AdminDao.class);
		AdminServiceImpl adminService = new AdminServiceImpl(adminDao);
		String email = "nonexistent@gmail.com";
		when(adminDao.findById(eq(email))).thenReturn(java.util.Optional.empty());

		Admin result = adminService.getAdmin(email);

		assertNull(result);
		verify(adminDao, times(1)).findById(eq(email));
	}
}
