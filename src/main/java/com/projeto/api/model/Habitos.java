package com.projeto.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Habitos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // Define a chave estrangeira
    private Usuarios usuarios;
    private String nome;
    private String frequencia;
    private LocalDate criado_em;
}
