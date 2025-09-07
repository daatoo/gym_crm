package com.gymcrm.service.impl;

import lombok.RequiredArgsConstructor;
import com.gymcrm.dto.trainingtype.TrainingTypeDto;
import com.gymcrm.repository.TrainingTypeRepository;
import com.gymcrm.service.TrainingTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;

    @Override
    public List<TrainingTypeDto> getAll() {
        return trainingTypeRepository.findAll().stream()
                .map(t -> new TrainingTypeDto(t.getTrainingTypeId(), t.getTrainingTypeName()))
                .toList();
    }
}
