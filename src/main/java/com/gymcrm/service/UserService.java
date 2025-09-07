package com.gymcrm.service;

import com.gymcrm.entity.User;

public interface UserService {
    User createUser(String firstName, String lastName);
    void changePassword(String username, String oldPassword, String newPassword);
    void setActiveStatus(String username, boolean isActive);
    User getByUsername(String username);
    User authenticate(String username, String password);
    String getRawPassword();
}