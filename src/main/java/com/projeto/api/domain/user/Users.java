package com.projeto.api.domain.user;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @NotBlank(message = "O campo email é obrigatório.")
    private String email;
    @Size(min = 8, message = "A senha deve possuir 8 caracteres ou mais.")
    private String senha;
    private LocalDate data_criacao;
}
