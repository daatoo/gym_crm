package service;

import com.gymcrm.dao.impl.TraineeDaoImpl;
import com.gymcrm.entity.Trainee;
import com.gymcrm.service.impl.TraineeServiceImpl;
import com.gymcrm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraineeServiceTest {

    @Mock
    private TraineeDaoImpl traineeDao;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainee = new Trainee();
        trainee.setFirstName("Liam");
        trainee.setLastName("Anderson");
        trainee.setAddress("456 Elm Street");
        trainee.setDateOfBirth("1998-07-15");
        trainee.setUserId(1);
        trainee.setUserName("Liam.Anderson");
        trainee.setPassword("securePass123");
    }

    @Test
    void testCreateTrainee_Success() {
        when(userService.getTraineeId()).thenReturn(1);
        when(userService.generateUserName("Liam", "Anderson")).thenReturn("Liam.Anderson");
        when(userService.generatePassword()).thenReturn("securePass123");
        when(traineeDao.createTrainee(any(Trainee.class))).thenReturn(trainee);

        Trainee createdTrainee = traineeService.createTrainee(trainee);

        assertNotNull(createdTrainee);
        assertEquals(trainee.getFirstName(), createdTrainee.getFirstName());
        assertEquals(trainee.getLastName(), createdTrainee.getLastName());
        assertEquals(trainee.getAddress(), createdTrainee.getAddress());
        assertEquals(trainee.getDateOfBirth(), createdTrainee.getDateOfBirth());
        assertEquals("Liam.Anderson", createdTrainee.getUserName());
        assertEquals("securePass123", createdTrainee.getPassword());

        verify(userService, times(1)).getTraineeId();
        verify(userService, times(1)).generateUserName("Liam", "Anderson");
        verify(userService, times(1)).generatePassword();
        verify(traineeDao, times(1)).createTrainee(any());
    }

    @Test
    void testGetTrainee_Found() {
        when(traineeDao.getTrainee(trainee.getUserId())).thenReturn(Optional.of(trainee));

        Optional<Trainee> retrievedTrainee = traineeService.getTrainee(trainee.getUserId());

        assertTrue(retrievedTrainee.isPresent());
        assertEquals(trainee.getFirstName(), retrievedTrainee.get().getFirstName());

        verify(traineeDao, times(1)).getTrainee(trainee.getUserId());
    }

    @Test
    void testGetTrainee_NotFound_ReturnsEmpty() {
        when(traineeDao.getTrainee(100)).thenReturn(Optional.empty());

        Optional<Trainee> retrievedTrainee = traineeService.getTrainee(100);

        assertTrue(retrievedTrainee.isEmpty());
        verify(traineeDao, times(1)).getTrainee(100);
    }

    @Test
    void testUpdateTrainee_Success() {
        when(traineeDao.updateTrainer(trainee)).thenReturn(trainee);

        Trainee updatedTrainee = traineeService.updateTrainee(trainee);

        assertNotNull(updatedTrainee);
        assertEquals(trainee.getAddress(), updatedTrainee.getAddress());

        verify(traineeDao, times(1)).updateTrainer(trainee);
    }

    @Test
    void testDeleteTrainee_Success() {
        when(traineeDao.deleteTrainee(trainee.getUserId())).thenReturn(true);

        boolean isDeleted = traineeService.deleteTrainee(trainee.getUserId());

        assertTrue(isDeleted);
        verify(traineeDao, times(1)).deleteTrainee(trainee.getUserId());
    }
}
