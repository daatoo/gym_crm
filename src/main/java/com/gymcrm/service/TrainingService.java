package com.gymcrm.service;

import com.gymcrm.dto.CreateTrainingDto;
import com.gymcrm.dto.TrainingDto;
import com.gymcrm.entity.Training;


import java.time.LocalDate;
import java.util.List;

public interface TrainingService {

    TrainingDto addTraining(CreateTrainingDto dto);

    List<TrainingDto> getTrainingsForTrainee(String username, String password);

    List<TrainingDto> getTrainingsForTrainer(String username, String password);

    List<TrainingDto> getTrainingsForTrainee(String username, String password,
                                             LocalDate fromDate, LocalDate toDate,
                                             String trainerName, String trainingType);

    List<TrainingDto> getTrainingsForTrainer(String username, String password,
                                             LocalDate fromDate, LocalDate toDate,
                                             String traineeName);
}