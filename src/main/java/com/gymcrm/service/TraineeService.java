package com.gymcrm.service;

import com.gymcrm.dto.CreateTraineeDto;
import com.gymcrm.dto.PasswordChangeDto;
import com.gymcrm.dto.TraineeDto;
import com.gymcrm.entity.Trainee;

import java.util.List;

public interface TraineeService {

    // Open action
    TraineeDto createTrainee(CreateTraineeDto dto);

    // Protected actions (require username + password)
    TraineeDto getByUsername(String username, String password);
    void updateTrainee(String username, CreateTraineeDto dto, String password);
    void deleteByUsername(String username, String password);
    void toggleActive(String username, String password);
    void changePassword(PasswordChangeDto dto);
    void updateAssignedTrainers(String traineeUsername, List<Long> trainerIds);
    void updateTrainersList(String traineeUsername, List<Long> trainerIds);

}