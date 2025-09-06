package com.gymcrm.dao;

import com.gymcrm.entity.Trainee;

import java.util.Optional;

public interface TraineeDao {

    Trainee createTrainee(Trainee trainee);

    boolean deleteTrainee(int userId);

    Optional<Trainee> getTrainee(int userId);

    Trainee updateTrainer(Trainee trainee);

    boolean existsByName(String username);

}
