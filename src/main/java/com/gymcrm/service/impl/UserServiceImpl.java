package com.gymcrm.service.impl;

import com.gymcrm.dao.impl.TraineeDaoImpl;
import com.gymcrm.dao.impl.TrainerDaoImpl;
import com.gymcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserServiceImpl implements UserService {
    private TrainerDaoImpl trainerDao;
    private TraineeDaoImpl traineeDao;

    public UserServiceImpl(TrainerDaoImpl trainerDao, TraineeDaoImpl traineeDao) {
    }

    @Autowired
    public void setTrainerDao(TrainerDaoImpl trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setTraineeDao(TraineeDaoImpl traineeDao) {
        this.traineeDao = traineeDao;
    }

    private static int traineeId = 1;
    private static int trainerId = 1;

    @Override
    public boolean alreadyUsed(String username){
        return this.traineeDao.existsByName(username) || this.trainerDao.existsByName(username);
    }
    @Override
    public String generateUserName(String firstName, String lastName) {
        String temp = firstName + "." + lastName;
        String generated = temp;
        int i = 1;

        while (alreadyUsed(generated)) {
            generated = temp + i;
            i++;
        }
        return generated;
    }

    @Override
    public synchronized Integer getTrainerId() {
        return trainerId++;
    }
    @Override
    public synchronized Integer getTraineeId() {
        return traineeId++;
    }

    @Override
    public String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        int password_length = 10;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(password_length);
        for (int i = 0; i < password_length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

}
