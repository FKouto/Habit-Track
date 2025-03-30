package com.projeto.api.domain.habits;

import com.projeto.api.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Table(name = "habits")
@Entity(name = "habits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Habits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Define a chave estrangeira
    private User user;
    private String nome_habito;
    @Enumerated(EnumType.STRING)
    private Frequency frequencia;
    @Enumerated(EnumType.STRING)
    private Period periodo;
    private LocalDate criado_em;
}
