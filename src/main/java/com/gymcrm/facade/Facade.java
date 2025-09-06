package com.gymcrm.facade;

import com.gymcrm.entity.Trainee;
import com.gymcrm.entity.Trainer;
import com.gymcrm.entity.Training;
import com.gymcrm.service.impl.TraineeServiceImpl;
import com.gymcrm.service.impl.TrainerServiceImpl;
import com.gymcrm.service.impl.TrainingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Facade {
    private final TraineeServiceImpl traineeService;
    private final TrainerServiceImpl trainerService;
    private final TrainingServiceImpl trainingService;

    public Trainee createTrainee(Trainee trainee){
        return traineeService.createTrainee(trainee);
    }
    public Trainer createTrainer(Trainer trainer){
        return trainerService.createTrainer(trainer);
    }
    public Training createTraining(Training training){
        return trainingService.createTraining(training);
    }

    public Optional<Trainee> getTrainee(int id) {
        return traineeService.getTrainee(id);
    }
    public Optional<Trainer> getTrainer(int id) {
        return trainerService.getTrainer(id);
    }
    public Optional<Training> getTraining(int traineeId, int trainerId) {
        return trainingService.getTraining(traineeId, trainerId);
    }

    public Trainee updateTrainee(Trainee trainee) {
        return traineeService.updateTrainee(trainee);
    }
    public Trainer updateTrainer(Trainer trainer) {
        return trainerService.updateTrainer(trainer);
    }

    public boolean deleteTrainee(int id) {
        return traineeService.deleteTrainee(id);
    }



}