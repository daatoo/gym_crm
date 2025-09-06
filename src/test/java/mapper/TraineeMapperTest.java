package mapper;

import com.gymcrm.dto.TraineeDto;
import com.gymcrm.dto.UserDto;
import com.gymcrm.entity.Trainee;
import com.gymcrm.entity.User;
import com.gymcrm.mapper.TraineeMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TraineeMapperTest {

    private final TraineeMapper traineeMapper = new TraineeMapper();

    @Test
    void toDto_shouldMapAllFields() {

        User user = new User();
        user.setUserId(10);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("john.doe");
        user.setActive(true);

        Trainee trainee = new Trainee();
        trainee.setTraineeId(5L);
        trainee.setAddress("123 Main St");
        trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        trainee.setUser(user);



        TraineeDto dto = traineeMapper.toDto(trainee);


        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals("123 Main St", dto.getAddress());
        assertEquals(LocalDate.of(1990, 1, 1), dto.getDateOfBirth());

        UserDto userDto = dto.getUser();
        assertNotNull(userDto);
        assertEquals(10L, userDto.getId());
        assertEquals("John", userDto.getFirstName());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("john.doe", userDto.getUsername());
        assertTrue(userDto.getIsActive());
    }

    @Test
    void toDto_shouldReturnNull_whenInputIsNull() {
        TraineeDto dto = traineeMapper.toDto(null);
        assertNull(dto);
    }
}