package com.gymcrm.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.gymcrm.dto.login.PasswordChangeDto;
import com.gymcrm.entity.User;
import com.gymcrm.service.TraineeService;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.TrainingService;
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


    @GetMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.info("Attempting login for user: {}", username);
        try {
            User user = userService.authenticate(username, password);
            log.info("Login successful for user: {}", user.getUsername());
            return ResponseEntity.ok("Login successful for " + user.getUsername());
        } catch (RuntimeException e) {
            log.warn("Login failed for user: {}", username);
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody PasswordChangeDto dto) {
        log.info("Password change attempt for user: {}", dto.getUsername());
        try {
            userService.changePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());
            log.info("Password successfully changed for user: {}", dto.getUsername());
            return ResponseEntity.ok("Password changed successfully");
        } catch (RuntimeException e) {
            log.error("Password change failed for user: {} - {}", dto.getUsername(), e.getMessage());
            return ResponseEntity.status(401).body("Password change failed: " + e.getMessage());
        }
    }
}