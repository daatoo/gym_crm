package com.gymcrm.service;

import com.gymcrm.dto.training.TrainingAddDto;
import com.gymcrm.dto.training.TrainingCreateDto;
import com.gymcrm.dto.training.TrainingDto;
import com.gymcrm.entity.Training;


import java.time.LocalDate;
import java.util.List;

public interface TrainingService {

    void addTraining(TrainingAddDto dto, String password);


    List<TrainingDto> getTrainingsForTrainee(String username, String password);

    List<TrainingDto> getTrainingsForTrainer(String username, String password);

    List<TrainingDto> getTrainingsForTrainee(String username, String password,
                                             LocalDate fromDate, LocalDate toDate,
                                             String trainerName, String trainingType);

    List<TrainingDto> getTrainingsForTrainer(String username, String password,
                                             LocalDate fromDate, LocalDate toDate,
                                             String traineeName);
}