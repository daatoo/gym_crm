package com.gymcrm.mapper;


import com.gymcrm.dto.TraineeDto;
import com.gymcrm.dto.UserDto;
import com.gymcrm.entity.Trainee;
import org.springframework.stereotype.Component;

@Component
public class TraineeMapper {

    public TraineeDto toDto(Trainee trainee) {
        if (trainee == null) return null;

        UserDto userDto = new UserDto();
        userDto.setId(trainee.getUser().getUserId());
        userDto.setFirstName(trainee.getUser().getFirstName());
        userDto.setLastName(trainee.getUser().getLastName());
        userDto.setUsername(trainee.getUser().getUserName());
        userDto.setIsActive(trainee.getUser().isActive());

        TraineeDto dto = new TraineeDto();
        dto.setId(trainee.getTraineeId());
        dto.setAddress(trainee.getAddress());
        dto.setDateOfBirth(trainee.getDateOfBirth());
        dto.setUser(userDto);
        return dto;
    }
}