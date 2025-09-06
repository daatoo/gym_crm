package dao;

import com.gymcrm.dao.impl.TraineeDaoImpl;
import com.gymcrm.entity.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeDaoTest {

    private TraineeDaoImpl traineeDao;

    @BeforeEach
    public void setUp() {
        Map<Integer, Trainee> traineeStorage = new HashMap<>();
        traineeDao = new TraineeDaoImpl(traineeStorage);
    }

    private Trainee testingTrainee() {
        return new Trainee(
                "Liam",
                "Anderson",
                "securePass456",
                "Liam.Anderson",
                2,
                "1998-07-15",
                "456 Elm Street"
        );
    }

    @Test
    void testCreateTrainee() {
        Trainee trainee = testingTrainee();
        traineeDao.createTrainee(trainee);

        Optional<Trainee> retrievedTrainee = traineeDao.getTrainee(trainee.getUserId());

        assertTrue(retrievedTrainee.isPresent());
        assertEquals(trainee, retrievedTrainee.get());
    }

    @Test
    void testFindTraineeById() {
        Trainee trainee = testingTrainee();
        traineeDao.createTrainee(trainee);

        Trainee foundTrainee = traineeDao.getTrainee(trainee.getUserId()).orElse(null);

        assertNotNull(foundTrainee);
        assertEquals(trainee.getUserName(), foundTrainee.getUserName());
    }

    @Test
    public void testGetTrainee_NotFound_ReturnsEmpty() {
        Optional<Trainee> retrievedTrainee = traineeDao.getTrainee(99);

        assertTrue(retrievedTrainee.isEmpty());
    }

    @Test
    public void testUpdateTrainee_Success() {
        Trainee trainee = testingTrainee();
        traineeDao.createTrainee(trainee);

        trainee.setAddress("Wall St");
        traineeDao.updateTrainer(trainee);

        Optional<Trainee> retrievedTrainee = traineeDao.getTrainee(trainee.getUserId());

        assertTrue(retrievedTrainee.isPresent());
        assertEquals("Wall St", retrievedTrainee.get().getAddress(), "Address should be updated");
    }

    @Test
    public void testDeleteTrainee_Success() {
        Trainee trainee = testingTrainee();
        traineeDao.createTrainee(trainee);

        boolean isDeleted = traineeDao.deleteTrainee(trainee.getUserId());

        assertAll(
                () -> assertTrue(isDeleted, "Trainee should be deleted successfully"),
                () -> assertFalse(traineeDao.getTrainee(trainee.getUserId()).isPresent(), "Deleted trainee should not exist")
        );
    }
}
