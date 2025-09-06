package com.gymcrm.service;

public interface UserService {

    boolean alreadyUsed(String username);

    String generateUserName(String firstName, String lastName);

    Integer getTrainerId();

    Integer getTraineeId();

    String generatePassword();
}