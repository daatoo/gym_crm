package service;

import com.gymcrm.dao.impl.TrainerDaoImpl;
import com.gymcrm.entity.Trainer;
import com.gymcrm.service.impl.TrainerServiceImpl;
import com.gymcrm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerServiceTest {

    @Mock
    private TrainerDaoImpl trainerDao;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer = new Trainer();
        trainer.setFirstName("Oliver");
        trainer.setLastName("Martinez");
        trainer.setSpecialization("Yoga & Flexibility");
        trainer.setUserId(1);
        trainer.setUserName("Oliver.Martinez");
        trainer.setPassword("securePass452");
    }

    @Test
    void testCreateTrainer_Success() {
        when(userService.getTrainerId()).thenReturn(1);
        when(userService.generateUserName("Oliver", "Martinez")).thenReturn("Oliver.Martinez");
        when(userService.generatePassword()).thenReturn("securePass452");
        when(trainerDao.createTrainer(any(Trainer.class))).thenReturn(trainer);

        Trainer createdTrainer = trainerService.createTrainer(trainer);

        assertNotNull(createdTrainer);
        assertEquals(trainer.getFirstName(), createdTrainer.getFirstName());
        assertEquals(trainer.getLastName(), createdTrainer.getLastName());
        assertEquals(trainer.getSpecialization(), createdTrainer.getSpecialization());
        assertEquals("Oliver.Martinez", createdTrainer.getUserName());
        assertEquals("securePass452", createdTrainer.getPassword());

        verify(userService, times(1)).getTrainerId();
        verify(userService, times(1)).generateUserName("Oliver", "Martinez");
        verify(userService, times(1)).generatePassword();
        verify(trainerDao, times(1)).createTrainer(any());
    }

    @Test
    void testGetTrainer_Found() {
        when(trainerDao.getTrainer(trainer.getUserId())).thenReturn(Optional.of(trainer));

        Optional<Trainer> retrievedTrainer = trainerService.getTrainer(trainer.getUserId());

        assertTrue(retrievedTrainer.isPresent());
        assertEquals(trainer.getFirstName(), retrievedTrainer.get().getFirstName());

        verify(trainerDao, times(1)).getTrainer(trainer.getUserId());
    }

    @Test
    void testGetTrainer_NotFound_ReturnsEmpty() {
        when(trainerDao.getTrainer(100)).thenReturn(Optional.empty());

        Optional<Trainer> retrievedTrainer = trainerService.getTrainer(100);

        assertTrue(retrievedTrainer.isEmpty());
        verify(trainerDao, times(1)).getTrainer(100);
    }

    @Test
    void testUpdateTrainer_Success() {
        when(trainerDao.updateTrainer(trainer)).thenReturn(trainer);

        Trainer updatedTrainer = trainerService.updateTrainer(trainer);

        assertNotNull(updatedTrainer);
        assertEquals(trainer.getSpecialization(), updatedTrainer.getSpecialization());

        verify(trainerDao, times(1)).updateTrainer(trainer);
    }
}
