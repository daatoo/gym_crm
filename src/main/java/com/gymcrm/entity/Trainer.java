package com.gymcrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "trainer")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Trainer extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long trainerId;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingType Specialization;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public Trainer(TrainingType specialization, User user) {
        Specialization = specialization;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trainer)) return false;
        Trainer that = (Trainer) o;
        return getTrainerId() != null && getTrainerId().equals(that.getTrainerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainerId());
    }
}

