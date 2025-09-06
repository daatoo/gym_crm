package com.gymcrm.service.impl;

import com.gymcrm.dao.impl.TraineeDaoImpl;
import com.gymcrm.dto.CreateTraineeDto;
import com.gymcrm.dto.PasswordChangeDto;
import com.gymcrm.dto.TraineeDto;
import com.gymcrm.entity.Trainee;
import com.gymcrm.entity.Trainer;
import com.gymcrm.entity.User;
import com.gymcrm.mapper.TraineeMapper;
import com.gymcrm.repository.TraineeRepository;
import com.gymcrm.repository.TrainerRepository;
import com.gymcrm.repository.TrainingRepository;
import com.gymcrm.service.TraineeService;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final TrainerRepository trainerRepository;
    private final UserService userService;
    private final TrainingRepository trainingRepository;

    @Override
    @Transactional
    public TraineeDto createTrainee(CreateTraineeDto dto) {
        log.info("Creating new trainee for {} {}", dto.getUser().getFirstName(), dto.getUser().getLastName());

        User user = userService.createUser(
                dto.getUser().getFirstName(),
                dto.getUser().getLastName()
        );

        Trainee trainee = new Trainee(dto.getDateOfBirth(), dto.getAddress(), user);
        traineeRepository.save(trainee);

        log.info("Trainee created with username: {}", user.getUserName());
        return traineeMapper.toDto(trainee);
    }

    @Override
    public TraineeDto getByUsername(String username, String password) {
        log.debug("Authenticating and fetching trainee by username: {}", username);
        userService.authenticate(username, password);

        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Trainee not found: {}", username);
                    return new RuntimeException("Trainee not found");
                });

        return traineeMapper.toDto(trainee);
    }

    @Override
    @Transactional
    public void changePassword(PasswordChangeDto dto) {
        userService.changePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());
    }

    @Override
    @Transactional
    public void updateTrainee(String username, CreateTraineeDto dto, String password) {
        log.info("Updating trainee profile: {}", username);
        userService.authenticate(username, password);

        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));

        trainee.getUser().setFirstName(dto.getUser().getFirstName());
        trainee.getUser().setLastName(dto.getUser().getLastName());
        trainee.setAddress(dto.getAddress());
        trainee.setDateOfBirth(dto.getDateOfBirth());

        traineeRepository.save(trainee);
        log.info("Trainee updated: {}", username);
    }

    @Override
    @Transactional
    public void toggleActive(String username, String password) {
        log.info("Toggling active status for trainee: {}", username);
        userService.authenticate(username, password);
        userService.toggleActive(username);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username, String password) {
        log.warn("Deleting trainee with auth check: {}", username);
        userService.authenticate(username, password);

        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));

        trainingRepository.deleteAllByTrainee(trainee);
        traineeRepository.delete(trainee);

        log.warn("Trainee deleted: {}", username);
    }


    @Override
    @Transactional
    public void updateAssignedTrainers(String username, List<Long> trainerIds) {
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));

        List<Trainer> newTrainers = trainerRepository.findAllById(trainerIds);
        trainee.setTrainers(newTrainers);

        traineeRepository.save(trainee);
    }

    @Override
    @Transactional
    public void updateTrainersList(String traineeUsername, List<Long> trainerIds) {
        log.info("Updating trainers list for trainee: {}", traineeUsername);

        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));

        List<Trainer> trainers = trainerRepository.findAllById(trainerIds);
        if (trainers.size() != trainerIds.size()) {
            throw new RuntimeException("One or more trainer IDs are invalid.");
        }

        trainee.setTrainers(trainers);
        traineeRepository.save(trainee);

        log.info("Trainee '{}' trainers updated: {}", traineeUsername, trainerIds);
    }


}