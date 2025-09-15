package com.gymcrm.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.gymcrm.dto.login.PasswordChangeDto;
import com.gymcrm.entity.User;
import com.gymcrm.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "User authentication and password change")
public class AuthController {

    private final UserService userService;
    private final MeterRegistry meterRegistry;

    @GetMapping("/login")
    @Operation(summary = "Login (returns 200 if credentials are valid)")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.info("Login attempt for user: {}", username);
        // Timer for login latency
        Timer.Sample sample = Timer.start(meterRegistry);
        try {

            User user = userService.authenticate(username, password); // may throw -> handled by @ControllerAdvice
            log.info("Login successful for user: {}", user.getUsername());
            // Counter (success)
            meterRegistry.counter(
                    "gymcrm_auth_login_attempts",
                    "result", "success"
            ).increment();
            return ResponseEntity.ok("Login successful");
        }catch (Exception ex) {
            // Counter (failure)
            meterRegistry.counter(
                    "gymcrm_auth_login_attempts",
                    "result", "failure"
            ).increment();
            throw ex;
        }finally {
            sample.stop(Timer.builder("gymcrm_auth_login_latency_seconds")
                    .description("Login latency")
                    .tag("controller", "AuthController")
                    .register(meterRegistry));
        }
    }

    @PutMapping("/password")
    @Operation(summary = "Change password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordChangeDto dto) {
        log.info("Password change requested for user: {}", dto.getUsername());
        userService.changePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword()); // may throw -> handled globally
        log.info("Password changed for user: {}", dto.getUsername());
        return ResponseEntity.ok("Password changed successfully");
    }
}
