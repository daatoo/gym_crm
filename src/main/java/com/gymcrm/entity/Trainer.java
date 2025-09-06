package com.gymcrm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Trainer extends User{

    private String specialization;

    public Trainer(String firstName, String lastName, String username,Integer userId, String password, String specialization) {
        super(firstName,lastName,username,password,userId,true);


        this.specialization = specialization;
    }
}

