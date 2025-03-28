package com.projeto.api.domain.habits;

import jakarta.validation.constraints.NotBlank;

public record HabitDTO(@NotBlank String nomeHabito, Frequency frequencia, Period period) {
}