package dao;

import com.gymcrm.dao.impl.TrainingDaoImpl;
import com.gymcrm.entity.Training;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingDaoTest {

    private TrainingDaoImpl trainingDao;

    @BeforeEach
    public void setUp(){
        Map<Pair<Integer, Integer>, Training> trainingStorage = new HashMap<>();
        this.trainingDao = new TrainingDaoImpl(trainingStorage);
    }

    private Training testingTraining() {
        return new Training(
                1,
                2,
                "Deep Stretch Yoga",

                "2023-08-15",
                Duration.ofMinutes(60),
                "Yoga"
        );
    }

    @Test
    void testCreateTraining() {
        Training training = testingTraining();
        trainingDao.createTraining(training);

        Optional<Training> retrievedTraining = trainingDao.getTraining(training.getTrainerId(), training.getTraineeId());

        assertTrue(retrievedTraining.isPresent());
        assertEquals(training, retrievedTraining.get());
    }

    @Test
    void testFindTrainingById() {
        Training training = testingTraining();
        trainingDao.createTraining(training);

        Training foundTraining = trainingDao.getTraining(training.getTrainerId(), training.getTraineeId()).orElse(null);

        assertNotNull(foundTraining);
        assertEquals(training.getTrainingName(), foundTraining.getTrainingName());
    }

    @Test
    public void testGetTraining_NotFound_ReturnsEmpty() {
        Optional<Training> retrievedTraining = trainingDao.getTraining(200, 200);
        assertTrue(retrievedTraining.isEmpty());
    }

    @Test
    public void testGetTraining_Found() {
        Training training = testingTraining();
        trainingDao.createTraining(training);

        Optional<Training> retrievedTraining = trainingDao.getTraining(training.getTrainerId(), training.getTraineeId());

        assertTrue(retrievedTraining.isPresent(), "Expected training to be present");
        assertEquals(training, retrievedTraining.get(), "Expected retrieved training to match the saved training");
    }


}
