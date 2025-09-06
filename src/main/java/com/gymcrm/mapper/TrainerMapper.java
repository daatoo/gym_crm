package com.gymcrm.mapper;

import com.gymcrm.dto.TrainerDto;
import com.gymcrm.dto.UserDto;
import com.gymcrm.entity.Trainer;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapper {

    public TrainerDto toDto(Trainer trainer) {
        if (trainer == null || trainer.getUser() == null) return null;

        UserDto userDto = new UserDto();
        userDto.setId(trainer.getUser().getUserId());
        userDto.setFirstName(trainer.getUser().getFirstName());
        userDto.setLastName(trainer.getUser().getLastName());
        userDto.setUsername(trainer.getUser().getUserName());
        userDto.setIsActive(trainer.getUser().isActive());

        TrainerDto dto = new TrainerDto();
        dto.setId(trainer.getTrainerId());
        dto.setSpecialization(trainer.getSpecialization().getTrainingTypeName());
        dto.setUser(userDto);

        return dto;
    }
}