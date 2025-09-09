package com.gymcrm.dto;


import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private Boolean isActive;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}