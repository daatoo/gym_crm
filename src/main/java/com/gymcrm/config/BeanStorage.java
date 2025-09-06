package com.gymcrm.config;

import com.gymcrm.entity.Trainee;
import com.gymcrm.entity.Trainer;
import com.gymcrm.entity.Training;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanStorage {
    @Bean
    public Map<Integer, Trainee> traineeStorage() {
        return new HashMap<>();
    }
    @Bean
    public Map<Integer, Trainer> trainerStorage() {
        return new HashMap<>();
    }
    @Bean
    public Map<Pair<Integer,Integer>, Training> trainingStorage() {
        return new HashMap<>();
    }

}
