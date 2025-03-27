package com.projeto.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O campo nome é obrigatório.")
    private String nome;
    private String email;
    private String senha;
    private LocalDate data_criacao;
}
