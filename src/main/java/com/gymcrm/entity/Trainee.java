package com.gymcrm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Trainee extends User{
    private String dateOfBirth;
    private String address;

    public Trainee(String firstName,String lastName,  String password,String userName, int id,String dateOfBirth, String address) {
        super(firstName,lastName,userName,password,id,true);

        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
}

