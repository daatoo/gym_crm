package com.gymcrm.service.impl;

import com.gymcrm.dao.impl.TrainerDaoImpl;
import com.gymcrm.dto.CreateTrainerDto;
import com.gymcrm.dto.PasswordChangeDto;
import com.gymcrm.dto.TrainerDto;
import com.gymcrm.entity.Trainer;
import com.gymcrm.entity.Trainee;
import com.gymcrm.entity.TrainingType;
import com.gymcrm.entity.User;
import com.gymcrm.mapper.TrainerMapper;
import com.gymcrm.repository.TraineeRepository;
import com.gymcrm.repository.TrainerRepository;
import com.gymcrm.repository.TrainingTypeRepository;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerMapper trainerMapper;
    private final UserService userService;

    @Override
    @Transactional
    public TrainerDto createTrainer(CreateTrainerDto dto) {
        log.info("Creating new trainer for {} {}", dto.getUser().getFirstName(), dto.getUser().getLastName());

        TrainingType specialization = trainingTypeRepository.findByTrainingTypeName(dto.getSpecialization())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        User user = userService.createUser(
                dto.getUser().getFirstName(),
                dto.getUser().getLastName()
        );

        Trainer trainer = new Trainer(specialization, user);
        trainerRepository.save(trainer);

        log.info("Trainer created with username: {}", user.getUserName());
        return trainerMapper.toDto(trainer);
    }

    @Override
    public TrainerDto getByUsername(String username, String password) {
        log.debug("Authenticating and fetching trainer: {}", username);
        userService.authenticate(username, password);

        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Trainer not found: {}", username);
                    return new RuntimeException("Trainer not found");
                });

        return trainerMapper.toDto(trainer);
    }

    @Override
    @Transactional
    public void changePassword(PasswordChangeDto dto) {
        userService.changePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());
    }

    @Override
    @Transactional
    public void updateTrainer(String username, CreateTrainerDto dto, String password) {
        log.info("Updating trainer: {}", username);
        userService.authenticate(username, password);

        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        trainer.getUser().setFirstName(dto.getUser().getFirstName());
        trainer.getUser().setLastName(dto.getUser().getLastName());

        TrainingType specialization = trainingTypeRepository.findByTrainingTypeName(dto.getSpecialization())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        trainer.setSpecialization(specialization);
        trainerRepository.save(trainer);
        log.info("Trainer updated: {}", username);
    }

    @Override
    @Transactional
    public void toggleActive(String username, String password) {
        userService.authenticate(username, password);
        userService.toggleActive(username);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username, String password) {
        log.warn("Deleting trainer by username: {}", username);
        userService.authenticate(username, password);

        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        trainerRepository.delete(trainer);
        log.warn("Trainer deleted: {}", username);
    }

    public List<TrainerDto> getUnassignedTrainersForTrainee(String traineeUsername) {
        Trainee trainee = traineeRepository.findWithTrainersByUserUsername(traineeUsername)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));

        List<Trainer> allTrainers = trainerRepository.findAll();

        return allTrainers.stream()
                .filter(trainer -> !trainee.getTrainers().contains(trainer))
                .map(trainerMapper::toDto)
                .toList();
    }

}