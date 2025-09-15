package com.gymcrm.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Login (returns 200 if credentials are valid)")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        log.info("Login attempt for user: {}", username);
        User user = userService.authenticate(username, password); // may throw -> handled by @ControllerAdvice
        log.info("Login successful for user: {}", user.getUserName());
        return ResponseEntity.ok("Login successful");
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
