package com.gymcrm.dao;

import com.gymcrm.entity.Training;

import java.util.Optional;

public interface TrainingDao {

    Training createTraining(Training training);

    Optional<Training> getTraining(int trainerId, int traineeId);
}
