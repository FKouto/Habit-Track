package com.projeto.api.controller;

import com.projeto.api.model.Habits;
import com.projeto.api.model.Users;
import com.projeto.api.repository.HabitsRepository;
import com.projeto.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/habits")
public class HabitsController {
    private final HabitsRepository habitsRepository;
    private final UserRepository userRepository;

    public HabitsController(HabitsRepository habitsRepository, UserRepository userRepository) {
        this.habitsRepository = habitsRepository;
        this.userRepository = userRepository;
    }

    // Criar um habito
    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody Habits habits) {
        Optional<Users> userOptional = userRepository.findById(habits.getUsers().getId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Usuário não encontrado."));
        }

        habits.setCriado_em(LocalDate.now());
        Habits savedHabits = habitsRepository.save(habits);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedHabits);
    }
}
