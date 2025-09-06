package com.gymcrm.service;

import com.gymcrm.entity.Training;

import java.util.Optional;

public interface TrainingService {

    Training createTraining(Training training);

    Optional<Training> getTraining(int traineeId, int trainerId);
}