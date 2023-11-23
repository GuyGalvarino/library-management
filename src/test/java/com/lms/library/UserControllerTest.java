package com.lms.library;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.lms.library.controller.UserController;
import com.lms.library.entities.Otp;
import com.lms.library.entities.User;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.OtpService;
import com.lms.library.services.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthorizationService authorizationService;

    @MockBean
    private OtpService otpService;

    @Test
    public void testGetUser_Success() throws Exception {
        // Mock the behavior of authorizationService as needed
        when(authorizationService.verifyToken(any(), any())).thenReturn(true);

        // Mock the behavior of userService
        when(userService.getUserByEmail(any())).thenReturn(new User("John Doe", "john@example.com", "hashedPassword"));

        mockMvc.perform(get("/users/{email}", "john@example.com")
                .header("Authorization", "Bearer validToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        // Mock the behavior of userService
        when(userService.getUserByEmail(any())).thenReturn(new User("John Doe", "john@example.com", "hashedPassword"));

        mockMvc.perform(post("/users/login")
                .content("{\"email\": \"john@example.com\", \"password\": \"password\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void testSigninUser_Success() throws Exception {
        // Mock the behavior of userService
        when(userService.getUserByEmail(any())).thenReturn(null);

        mockMvc.perform(post("/users/signin")
                .content("{\"name\": \"John Doe\", \"email\": \"john@example.com\", \"password\": \"password\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("A OTP has been sent to your email"));
    }

    @Test
    public void testVerifyUser_Success() throws Exception {
        // Mock the behavior of otpService
        when(otpService.verifyOtp(any(), any())).thenReturn(new Otp("john@example.com" , "John Doe", "hashedPassword","123456"));

        mockMvc.perform(post("/users/verify-otp")
                .content("{\"otp\": \"123456\", \"email\": \"john@example.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        // Mock the behavior of authorizationService as needed
        when(authorizationService.verifyToken(any(), any())).thenReturn(true);

        // Mock the behavior of userService
        when(userService.deleteUser(any())).thenReturn(new User("John Doe", "john@example.com", "hashedPassword"));

        mockMvc.perform(delete("/users/{userId}", 123)
                .header("Authorization", "Bearer validToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}
