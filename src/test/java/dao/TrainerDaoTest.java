package dao;

import com.gymcrm.dao.impl.TrainerDaoImpl;
import com.gymcrm.entity.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerDaoTest {

    private TrainerDaoImpl trainerDao;

    @BeforeEach
    public void setUp(){
        Map<Integer, Trainer> trainerStorage = new HashMap<>();
        this.trainerDao = new TrainerDaoImpl(trainerStorage);
    }

    private Trainer testingTrainer() {
        return new Trainer(
                "Oliver",
                "Martinez",
                "Oliver.Martinez",
                1,
                "securePass452",
                "Yoga & Flexibility"
        );
    }

    @Test
    void testCreateTrainer(){
        Trainer trainer = testingTrainer();
        trainerDao.createTrainer(trainer);
        assertEquals(trainer, trainerDao.getTrainer(trainer.getUserId()).orElse(null));
    }

    @Test
    void testFindTrainerById() {
        Trainer trainer = testingTrainer();
        trainerDao.createTrainer(trainer);

        Trainer foundTrainer = trainerDao.getTrainer(trainer.getUserId()).orElse(null);
        assertNotNull(foundTrainer);
        assertEquals(trainer.getSpecialization(), foundTrainer.getSpecialization());
    }

    @Test
    public void testGetTrainer_NotFound_ReturnsEmpty() {
        Optional<Trainer> retrievedTrainer = trainerDao.getTrainer(100);
        assertTrue(retrievedTrainer.isEmpty());
    }

    @Test
    public void testUpdateTrainer_Success() {
        Trainer trainer = testingTrainer();
        trainerDao.createTrainer(trainer);

        trainer.setSpecialization("Strength Training");
        trainerDao.updateTrainer(trainer);

        Optional<Trainer> retrievedTrainer = trainerDao.getTrainer(trainer.getUserId());

        assertTrue(retrievedTrainer.isPresent());
        assertEquals("Strength Training", retrievedTrainer.get().getSpecialization());
    }


}
