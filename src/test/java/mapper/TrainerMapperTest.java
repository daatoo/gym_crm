package mapper;

import com.gymcrm.dto.TrainerDto;
import com.gymcrm.dto.UserDto;
import com.gymcrm.entity.Trainer;
import com.gymcrm.entity.TrainingType;
import com.gymcrm.entity.User;
import com.gymcrm.mapper.TrainerMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainerMapperTest {

    private final TrainerMapper trainerMapper = new TrainerMapper();

    @Test
    void toDto_shouldMapAllFields() {

        User user = new User();
        user.setUserId(20);
        user.setFirstName("Alice");
        user.setLastName("Smith");
        user.setUserName("alice.smith");
        user.setActive(false);


        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Crossfit");


        Trainer trainer = new Trainer();
        trainer.setTrainerId(7L);
        trainer.setUser(user);
        trainer.setSpecialization(trainingType);

        TrainerDto dto = trainerMapper.toDto(trainer);


        assertNotNull(dto);
        assertEquals(7L, dto.getId());
        assertEquals("Crossfit", dto.getSpecialization());

        UserDto userDto = dto.getUser();
        assertNotNull(userDto);
        assertEquals(20L, userDto.getId());
        assertEquals("Alice", userDto.getFirstName());
        assertEquals("Smith", userDto.getLastName());
        assertEquals("alice.smith", userDto.getUsername());
        assertFalse(userDto.getIsActive());
    }

    @Test
    void toDto_shouldReturnNull_whenInputIsNull() {
        assertNull(trainerMapper.toDto(null));
    }

    @Test
    void toDto_shouldReturnNull_whenUserIsNull() {
        Trainer trainer = new Trainer();
        trainer.setUser(null);
        assertNull(trainerMapper.toDto(trainer));
    }
}