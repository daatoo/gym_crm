package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import com.gymcrm.controller.AuthController;
import com.gymcrm.dto.login.PasswordChangeDto;
import com.gymcrm.entity.User;
import com.gymcrm.service.UserService;
import com.gymcrm.util.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthController authController;

    @Mock
    private UserService userService;

    private MeterRegistry meterRegistry;

    private MockMvc mockMvc;
    private ObjectMapper om;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        meterRegistry = new SimpleMeterRegistry();               // <-- add this
        authController = new AuthController(userService, meterRegistry); // <-- pass it in

        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        om = new ObjectMapper();
    }

    @Test
    void login_shouldReturnSuccessResponse_whenCredentialsAreValid() {
        User user = new User();
        user.setUsername("john.doe");
        when(userService.authenticate("john.doe", "password")).thenReturn(user);

        ResponseEntity<String> response = authController.login("john.doe", "password");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Login successful", response.getBody());

        // Optional: assert custom metrics
        assertEquals(1.0,
                meterRegistry.find("gymcrm_auth_login_attempts")
                        .tags("result", "success")
                        .counter().count(),
                1e-9);

        Timer t = meterRegistry.find("gymcrm_auth_login_latency_seconds")
                .tag("controller", "AuthController")
                .timer();
        assertNotNull(t);
        assertEquals(1, t.count());
    }

    @Test
    void login_shouldThrow_whenCredentialsAreInvalid() {
        when(userService.authenticate("john.doe", "wrong"))
                .thenThrow(new RuntimeException("Invalid username or password"));

        RuntimeException ex =
                assertThrows(RuntimeException.class, () -> authController.login("john.doe", "wrong"));
        assertEquals("Invalid username or password", ex.getMessage());

        // Optional: assert failure counter
        assertEquals(1.0,
                meterRegistry.find("gymcrm_auth_login_attempts")
                        .tags("result", "failure")
                        .counter().count(),
                1e-9);
    }

    @Test
    void changePassword_shouldReturnSuccessResponse_whenValid() {
        PasswordChangeDto dto = new PasswordChangeDto("john.doe", "oldPass", "newPass");
        doNothing().when(userService).changePassword("john.doe", "oldPass", "newPass");

        ResponseEntity<String> response = authController.changePassword(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Password changed successfully", response.getBody());
    }

    @Test
    void changePassword_shouldThrow_whenChangeFails_unitStyle() {
        PasswordChangeDto dto = new PasswordChangeDto("john.doe", "wrongOld", "newPass");
        doThrow(new IllegalArgumentException("Old password does not match."))
                .when(userService)
                .changePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> authController.changePassword(dto));
        assertTrue(ex.getMessage().contains("Old password does not match."));
    }
}
