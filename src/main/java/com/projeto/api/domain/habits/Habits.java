package com.projeto.api.domain.habits;

import com.projeto.api.domain.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Habits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Define a chave estrangeira
    private Users users;
    private String nome;
    private String frequencia;
    private LocalDate criado_em;
}
