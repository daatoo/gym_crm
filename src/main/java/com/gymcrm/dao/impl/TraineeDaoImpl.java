package com.gymcrm.dao.impl;

import com.gymcrm.dao.TraineeDao;
import com.gymcrm.entity.Trainee;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log
public class TraineeDaoImpl implements TraineeDao {
    private final Map<Integer, Trainee> traineeStorage;


    @Override
    public Trainee createTrainee(Trainee trainee) {
        this.traineeStorage.put(trainee.getUserId(), trainee);
        return trainee;
    }

    @Override
    public boolean deleteTrainee(int userId) {
        return traineeStorage.remove(userId) != null;
    }

    @Override
    public Optional<Trainee> getTrainee(int userId) {
        return Optional.ofNullable(traineeStorage.get(userId))
                .or(() -> {
                    log.warning("Trainer with ID " + userId + " not found");
                    return Optional.empty();
                });
    }


    @Override
    public Trainee updateTrainer(Trainee trainee) {
        if (!traineeStorage.containsKey(trainee.getUserId())) {
            log.warning("no trainer found");
        }
        traineeStorage.put(trainee.getUserId(), trainee);
        return trainee;
    }

    @Override
    public boolean existsByName(String username) {
        return traineeStorage.values().stream()
                .anyMatch(trainee -> trainee.getUserName().equalsIgnoreCase(username));
    }
}
