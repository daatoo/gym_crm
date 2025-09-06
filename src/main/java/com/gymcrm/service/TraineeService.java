package com.gymcrm.service;

import com.gymcrm.entity.Trainee;

import java.util.Optional;

public interface TraineeService {

    Trainee createTrainee(Trainee trainee);

    Trainee updateTrainee(Trainee trainee);

    boolean deleteTrainee(int userId);

    Optional<Trainee> getTrainee(int userId);

}