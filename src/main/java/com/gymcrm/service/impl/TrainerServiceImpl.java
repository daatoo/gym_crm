package com.gymcrm.service.impl;

import com.gymcrm.dao.impl.TrainerDaoImpl;
import com.gymcrm.entity.Trainer;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log

public class TrainerServiceImpl implements TrainerService {
    private UserService userService;
    private TrainerDaoImpl trainerDao;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTrainerDao(TrainerDaoImpl trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {

        trainer.setUserId(userService.getTrainerId());
        trainer.setUserName(userService.generateUserName(trainer.getFirstName(), trainer.getLastName()));
        trainer.setPassword(userService.generatePassword());

        log.info("Create trainer");
        return trainerDao.createTrainer(trainer);
    }
    @Override
    public Optional<Trainer> getTrainer(int trainerId) {
        log.info("Get trainer");
        return trainerDao.getTrainer(trainerId);
    }
    @Override
    public Trainer updateTrainer(Trainer trainer) {
        log.info("Update trainer");
        return trainerDao.updateTrainer(trainer);
    }
}
