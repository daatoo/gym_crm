package com.gymcrm.service;

import com.gymcrm.dto.trainee.TraineeCredentialsDto;
import com.gymcrm.dto.trainer.*;
import com.gymcrm.dto.login.PasswordChangeDto;
import com.gymcrm.dto.training.TrainerTrainingRequestDto;
import com.gymcrm.dto.training.TrainerTrainingResponseDto;

import java.util.List;

public interface TrainerService {
    TraineeCredentialsDto registerWithCredentials(TrainerCreateDto dto);
    TrainerProfileDto getTrainerProfile(String username, String password);
    TrainerProfileDto updateTrainerProfile(TrainerUpdateDto dto, String password);
    boolean toggleActive(String username, boolean isActive, String password);
    List<TrainerForTrainerListDto> getUnassignedTrainersForTrainee(String traineeUsername, String password);
    List<TrainerTrainingResponseDto> getTrainerTrainingsList(TrainerTrainingRequestDto dto, String password);
}
