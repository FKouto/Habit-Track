package com.projeto.api.domain.habits;

import com.projeto.api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "habits_record")
@Entity(name = "habits_record")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class HabitsRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Define a chave estrangeira
    private User user;
    @ManyToOne
    @JoinColumn(name = "habits_id", nullable = false) // Define a chave estrangeira
    private Habits habits;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Period period;
}
