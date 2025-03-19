package com.projeto.api.model;

import com.projeto.api.enums.Periodo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class FrequenciaHabitos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // Define a chave estrangeira
    private Usuarios usuarios;
    @ManyToOne
    @JoinColumn(name = "habitos_id", nullable = false) // Define a chave estrangeira
    private Habitos habitos;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Periodo periodo;
    private boolean realizado;
}
