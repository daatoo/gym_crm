package com.gymcrm.dao;

import com.gymcrm.entity.Trainer;

import java.util.Optional;

public interface TrainerDao {

    Trainer createTrainer(Trainer trainer);

    Optional<Trainer> getTrainer(int userId);

    Trainer updateTrainer(Trainer trainer);

    boolean existsByName(String username);

}
