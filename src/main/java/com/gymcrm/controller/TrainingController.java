package com.gymcrm.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.gymcrm.dto.training.TrainingAddDto;
import com.gymcrm.service.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Training Management", description = "Endpoints for managing trainings")
public class TrainingController {

    private final TrainingService trainingService;
    private final MeterRegistry meterRegistry;

    @PostMapping("/add")
    @Operation(summary = "Add a new training (authentication required)")
    public ResponseEntity<String> addTraining(
            @Valid @RequestBody TrainingAddDto dto,
            @RequestParam String password
    ) {
        log.info("Adding training for trainee: {} by trainer: {}", dto.getTraineeUsername(), dto.getTrainerUsername());
        // start latency timer
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            trainingService.addTraining(dto, password);
            // Counter tagged by trainingName
            String trainingName = dto.getTrainingName() != null ? dto.getTrainingName() : "unknown";
            meterRegistry.counter(
                    "gymcrm_training_create",
                    "training_name", trainingName
            ).increment();
            return ResponseEntity.ok("Training successfully added");
        }finally {
            sample.stop(Timer.builder("gymcrm_training_add_latency_seconds")
                    .description("Latency for adding a training")
                    .tag("controller", "TrainingController")
                    .register(meterRegistry));
        }
    }
}
