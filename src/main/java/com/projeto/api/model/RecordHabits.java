package com.projeto.api.model;

import com.projeto.api.enums.Period;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class RecordHabits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Define a chave estrangeira
    private Users users;
    @ManyToOne
    @JoinColumn(name = "habits_id", nullable = false) // Define a chave estrangeira
    private Habits habits;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Period period;
    private boolean realizado;
}
