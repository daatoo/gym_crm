package com.gymcrm.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymcrm.entity.Trainee;
import com.gymcrm.entity.Trainer;
import com.gymcrm.entity.Training;
import com.gymcrm.service.impl.TraineeServiceImpl;
import com.gymcrm.service.impl.TrainerServiceImpl;
import com.gymcrm.service.impl.TrainingServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreLoader {

    private final TrainingServiceImpl trainingService;
    private final TrainerServiceImpl trainerService;
    private final TraineeServiceImpl traineeService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${data.storage}")
    private String dataFilePath;

    @PostConstruct
    public void loadData() {
        log.info("Loading data from: {} " , dataFilePath);

        try {
            File file = new File(dataFilePath);
            DataWrapper dataWrapper = objectMapper.readValue(file,DataWrapper.class);
            for (Trainee traineeData : dataWrapper.getTrainees()) {
                Trainee trainee = new Trainee(
                        traineeData.getFirstName(),
                        traineeData.getLastName(),
                        traineeData.getPassword(),
                        traineeData.getUserName(),
                        traineeData.getUserId(),
                        traineeData.getDateOfBirth(),
                        traineeData.getAddress()
                );
                Trainee savedTrainee = traineeService.createTrainee(trainee);
                log.info("Created Trainee: {}", savedTrainee.getUserName());
            }
            for (Trainer trainerData : dataWrapper.getTrainers()) {
                Trainer trainer = new Trainer(
                        trainerData.getFirstName(),
                        trainerData.getLastName(),
                        trainerData.getUserName(),
                        trainerData.getUserId(),
                        trainerData.getPassword(),
                        trainerData.getSpecialization()
                );
                Trainer savedTrainer = trainerService.createTrainer(trainer);
                log.info("Created Trainer: {}", savedTrainer.getUserName());
            }
            for (TrainingData trainingData : dataWrapper.getTrainings()) {
                Training training = new Training(
                        trainingData.getTraineeId(),
                        trainingData.getTrainerId(),
                        trainingData.getTrainingName(),
                        trainingData.getTrainingDate(),
                        Duration.ofMinutes(trainingData.getTrainingDurationMinutes()),
                        trainingData.getTrainingType()
                );
                Training savedTraining = trainingService.createTraining(training);
                log.info("Created Training: {}", savedTraining.getTrainingName());

            }


        } catch (IOException e) {
            log.error("Error reading data file: {}", e.getMessage());
        }
    }
    // this class is used to map the structure of json file
    // and ensures it is properly structured before being used
    @Getter
    static class DataWrapper {
        private List<Trainee> trainees;
        private List<Trainer> trainers;
        private List<TrainingData> trainings;
    }

    @Getter
    static class TrainingData {
        private int traineeId;
        private int trainerId;
        private String trainingName;
        private String trainingType;
        private String trainingDate;
        private int trainingDurationMinutes;
    }

}
