package service;

import com.gymcrm.dao.impl.TraineeDaoImpl;
import com.gymcrm.dao.impl.TrainerDaoImpl;
import com.gymcrm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.*;


import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private TraineeDaoImpl traineeDao;

    @Mock
    private TrainerDaoImpl trainerDao;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(trainerDao, traineeDao);
    }

    @Test
    void testGenerateUserName_Unique() {
        String username = userService.generateUserName("Unique", "Username");
        assertEquals("Unique.Username", username);
    }

    @Test
    void testAlreadyUsed_NotExists() {
        assertFalse(userService.alreadyUsed("Non.Existing"));
    }

    @Test
    void testGeneratePassword_Length() {
        String password = userService.generatePassword();
        assertEquals(10, password.length());
    }

    @Test
    void testGetTrainerId_Increments() {
        int firstId = userService.getTrainerId();
        int secondId = userService.getTrainerId();

        assertEquals(firstId + 1, secondId);
    }

    @Test
    void testGetTraineeId_ShouldReturnIncrementingValues() {
        int firstId = userService.getTraineeId();
        int secondId = userService.getTraineeId();

        assertEquals(firstId + 1, secondId);
    }
}
