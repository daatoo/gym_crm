package com.gymcrm.service.impl;

import com.gymcrm.dao.impl.TraineeDaoImpl;
import com.gymcrm.entity.Trainee;
import com.gymcrm.service.TraineeService;
import com.gymcrm.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Log
public class TraineeServiceImpl implements TraineeService {
    private UserService userService;
    private TraineeDaoImpl traineeDao;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTraineeDao(TraineeDaoImpl traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Override
    public Trainee createTrainee(Trainee trainee) {

        trainee.setUserId(userService.getTraineeId());
        trainee.setUserName(userService.generateUserName(trainee.getFirstName(), trainee.getLastName()));
        trainee.setPassword(userService.generatePassword());

        log.info("Creating new trainee: {}");

        return traineeDao.createTrainee(trainee);
    }

    @Override
    public boolean deleteTrainee(int userId) {
        log.info("deleting trainee with ID: {}");
        return traineeDao.deleteTrainee(userId);
    }
    @Override
    public Optional<Trainee> getTrainee(int userId) {
        log.info("select trainee with id: {}");
        return traineeDao.getTrainee(userId);
    }
    @Override
    public Trainee updateTrainee(Trainee trainee) {
        log.info("Updating trainee with ID: {}");
        return traineeDao.updateTrainer(trainee);
    }
}

