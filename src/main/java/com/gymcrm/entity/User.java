package com.gymcrm.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean isActive = true;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.userName  = username;
        this.password  = password;
    }
}