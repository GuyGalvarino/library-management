package Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.lms.library.controller.AdminController;
import com.lms.library.services.AdminService;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.OtpService;

@WebMvcTest(AdminController.class)
public class AdminControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OtpService otpService;

    @MockBean
    private AdminService adminService;

    @MockBean
    private AuthorizationService authorizationService;
    
    @Test
    public void testGetOtp_Success() throws Exception {
        mockMvc.perform(post("/admin")
                .content("{\"email\":\"admin@example.com\", \"password\":\"password123\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OTP sent to the email of the admin"));
    }

    @Test
    public void testGetOtp_AdminNotFound() throws Exception {
        mockMvc.perform(post("/admin")
                .content("{\"email\":\"nonexistent@example.com\", \"password\":\"password123\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetOtp_IncorrectPassword() throws Exception {
        mockMvc.perform(post("/admin")
                .content("{\"email\":\"admin@example.com\", \"password\":\"incorrectPassword\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testValidateAdminLogin_Success() throws Exception {
        // Mock the behavior of otpService and authorizationService as needed
        mockMvc.perform(post("/admin/verify-otp")
                .content("{\"otp\":\"123456\", \"email\":\"admin@example.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.email").value("admin@example.com"))
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void testValidateAdminLogin_AdminNotFound() throws Exception {
        mockMvc.perform(post("/admin/verify-otp")
                .content("{\"otp\":\"123456\", \"email\":\"nonexistent@example.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testValidateAdminLogin_IncorrectOtp() throws Exception {
        mockMvc.perform(post("/admin/verify-otp")
                .content("{\"otp\":\"incorrectOtp\", \"email\":\"admin@example.com\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
