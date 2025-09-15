package com.gymcrm.service;

import com.gymcrm.dto.trainee.*;
import com.gymcrm.dto.trainer.TrainerForTrainerListDto;
import com.gymcrm.dto.training.TraineeTrainingRequestDto;
import com.gymcrm.dto.training.TraineeTrainingResponseDto;

import java.util.List;

public interface TraineeService {
    TraineeCredentialsDto registerWithCredentials(TraineeCreateDto dto);

    TraineeProfileDto getTraineeProfile(String username, String password);

    TraineeProfileDto updateProfile(TraineeProfileUpdateDto dto, String password);

    void deleteByUsername(String username, String password);

    boolean toggleActive(String username, boolean isActive, String password);

    List<TrainerForTrainerListDto> updateTraineeTrainers(TraineeTrainerUpdateDto dto, String password);

    List<TraineeTrainingResponseDto> getTraineeTrainingsList(TraineeTrainingRequestDto dto, String password);
}