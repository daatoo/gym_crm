package com.gymcrm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public abstract class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private int userId;
    private boolean isActive = true;
}