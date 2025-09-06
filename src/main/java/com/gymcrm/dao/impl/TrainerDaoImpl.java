package com.gymcrm.dao.impl;

import com.gymcrm.dao.TrainerDao;
import com.gymcrm.entity.Trainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log
public class TrainerDaoImpl implements TrainerDao {
    private final Map<Integer, Trainer> trainerStorage;

    @Override
    public Trainer createTrainer(Trainer trainer) {
        this.trainerStorage.put(trainer.getUserId(), trainer);
        return trainer;
    }
    @Override
    public Optional<Trainer> getTrainer(int userId) {
        return Optional.ofNullable(trainerStorage.get(userId))
                .or(() -> {
                    log.warning("Trainer with ID " + userId + " not found");
                    return Optional.empty();
                });
    }

    @Override
    public Trainer updateTrainer(Trainer trainer) {
        if (!trainerStorage.containsKey(trainer.getUserId())) {
            log.warning("no trainer found");
        }
        trainerStorage.put(trainer.getUserId(), trainer);
        return trainer;
    }
    @Override
    public boolean existsByName(String username) {
        return trainerStorage.values().stream()
                .anyMatch(trainer -> trainer.getUserName().equalsIgnoreCase(username));
    }

}
