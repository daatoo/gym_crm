package com.gymcrm.service;

import com.gymcrm.entity.Trainer;

import java.util.Optional;

public interface TrainerService {

    Trainer createTrainer(Trainer trainer);

    Optional<Trainer> getTrainer(int trainerId);

    Trainer updateTrainer(Trainer trainer);

}
