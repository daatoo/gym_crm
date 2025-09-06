package com.gymcrm.service.impl;

import com.gymcrm.dao.impl.TrainingDaoImpl;
import com.gymcrm.entity.Training;
import com.gymcrm.service.TrainingService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log

public class TrainingServiceImpl implements TrainingService {
    private TrainingDaoImpl trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDaoImpl trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Override
    public Training createTraining(Training training) {
        log.info("created Training");
        return trainingDao.createTraining(training);
    }
    @Override
    public Optional<Training> getTraining(int traineeId, int trainerId) {
        log.info("get Training");
        return trainingDao.getTraining(traineeId, trainerId);
    }
}
