package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import com.gymcrm.controller.TrainingController;
import com.gymcrm.dto.training.TrainingAddDto;
import com.gymcrm.service.TrainingService;
import com.gymcrm.util.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TrainingControllerTest {

    private MockMvc mockMvc;
    private TrainingService trainingService;
    private ObjectMapper om;
    private MeterRegistry meterRegistry;

    @BeforeEach
    void setUp() {
        trainingService = Mockito.mock(TrainingService.class);
        meterRegistry = new SimpleMeterRegistry(); // <-- add a test registry

        TrainingController controller = new TrainingController(trainingService, meterRegistry); // <-- pass it in

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        om = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    private TrainingAddDto validDto() {
        TrainingAddDto dto = new TrainingAddDto();
        dto.setTrainingName("Workout A");
        dto.setTrainingDate(LocalDate.of(2025, 8, 12));
        dto.setTrainingDuration(60L);
        dto.setTrainerUsername("trainer1");
        dto.setTraineeUsername("trainee1");
        return dto;
    }

    @Test
    void addTraining_shouldReturnOk_andRecordMetrics() throws Exception {
        doNothing().when(trainingService).addTraining(any(TrainingAddDto.class), eq("pass"));

        mockMvc.perform(post("/training/add")
                        .param("password", "pass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto())))
                .andExpect(status().isOk())
                .andExpect(content().string("Training successfully added"));

        // Assert the counter incremented for this training name
        double cnt = meterRegistry.find("gymcrm_training_create")
                .tag("training_name", "Workout A")
                .counter().count();
        assertEquals(1.0, cnt, 1e-9);

        // Assert the latency timer recorded one call
        Timer t = meterRegistry.find("gymcrm_training_add_latency_seconds")
                .tag("controller", "TrainingController")
                .timer();
        assertNotNull(t);
        assertEquals(1, t.count());
    }

    @Test
    void addTraining_shouldReturnNotFound_whenServiceThrowsRuntimeException_andNotIncrementCounter() throws Exception {
        doThrow(new RuntimeException("boom"))
                .when(trainingService).addTraining(any(TrainingAddDto.class), eq("pass"));

        mockMvc.perform(post("/training/add")
                        .param("password", "pass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto())))
                .andExpect(status().isNotFound());

        // Counter should not increment on failure
        assertNull(meterRegistry.find("gymcrm_training_create")
                .tag("training_name", "Workout A")
                .counter());
    }

    @Test
    void addTraining_shouldReturnBadRequest_onValidationErrors() throws Exception {
        mockMvc.perform(post("/training/add")
                        .param("password", "pass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addTraining_shouldReturnBadRequest_whenMissingPasswordParam() throws Exception {
        mockMvc.perform(post("/training/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto())))
                .andExpect(status().isBadRequest());

        // Method won’t run, so no timer/counter should exist
        assertNull(meterRegistry.find("gymcrm_training_add_latency_seconds").timer());
        assertNull(meterRegistry.find("gymcrm_training_create").counter());
    }
}
