package com.gymcrm.service;

import com.gymcrm.dto.CreateTrainerDto;
import com.gymcrm.dto.PasswordChangeDto;
import com.gymcrm.dto.TrainerDto;
import com.gymcrm.entity.Trainer;

import java.util.List;

public interface TrainerService {
    TrainerDto createTrainer(CreateTrainerDto dto);
    TrainerDto getByUsername(String username, String password);
    void changePassword(PasswordChangeDto dto);
    void updateTrainer(String username, CreateTrainerDto dto, String password);
    void toggleActive(String username, String password);
    void deleteByUsername(String username, String password);
    List<TrainerDto> getUnassignedTrainersForTrainee(String traineeUsername);
}