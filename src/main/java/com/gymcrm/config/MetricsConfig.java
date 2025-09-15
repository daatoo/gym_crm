package com.gymcrm.config;

import io.micrometer.core.instrument.MeterRegistry;
import com.gymcrm.repository.TrainingRepository;
import com.gymcrm.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Object usersCountGauge(MeterRegistry registry, UserRepository userRepository) {
        registry.gauge("gymcrm_users_count", userRepository, UserRepository::count);
        return new Object();
    }

    @Bean
    public Object trainingsCountGauge(MeterRegistry registry, TrainingRepository trainingRepository) {
        registry.gauge("gymcrm_trainings_count", trainingRepository, TrainingRepository::count);
        return new Object();
    }

    @Bean
    public Object metricsWarmup(MeterRegistry registry) {
        registry.counter("gymcrm_auth_login_attempts", "result", "success");
        registry.counter("gymcrm_auth_login_attempts", "result", "failure");
        return new Object();
    }
}
