package com.projeto.api.controllers;

import com.projeto.api.domain.habits.Frequency;
import com.projeto.api.domain.habits.HabitDTO;
import com.projeto.api.domain.habits.Habits;
import com.projeto.api.domain.habits.Period;
import com.projeto.api.domain.user.User;
import com.projeto.api.infra.security.TokenService;
import com.projeto.api.repositories.HabitsRepository;
import com.projeto.api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/habits")
public class HabitsController {

    @Autowired
    private HabitsRepository habitsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/create")
    public ResponseEntity<?> createHabit(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid HabitDTO data) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);
        UserDetails userDetails = userRepository.findByEmail(email);

        if (data.nomeHabito() == null || data.nomeHabito().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("message", "O nome do hábito não pode ser vazio."));
        }

        User user = (User) userDetails;
        Habits habit = new Habits(null, user, data.nomeHabito(), data.frequencia(), data.periodo(), LocalDate.now());
        habitsRepository.save(habit);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message:", "Hábito registrado com sucesso!"));
    }

    @PatchMapping("/updateName/{id}")
    public ResponseEntity<?> updateHabitName(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody Map<String, String> updates) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);
        UserDetails userDetails = userRepository.findByEmail(email);

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Habits habit = habitsRepository.findById(id).orElse(null);
        if (habit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Hábito não encontrado."));
        }

        if (!habit.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Você não tem permissão para atualizar este hábito."));
        }

        String newName = updates.get("nomeHabito");
        if (newName == null || newName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("message", "O nome do hábito não pode ser vazio."));
        }

        habit.setNome_habito(newName);
        habitsRepository.save(habit);

        return ResponseEntity.ok(Map.of("message", "Nome do hábito atualizado com sucesso."));
    }

    @PatchMapping("/updateFrequency/{id}")
    public ResponseEntity<?> updateHabitFrequency(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody Map<String, String> updates) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);
        UserDetails userDetails = userRepository.findByEmail(email);

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Habits habit = habitsRepository.findById(id).orElse(null);
        if (habit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Hábito não encontrado."));
        }

        if (!habit.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Você não tem permissão para atualizar este hábito."));
        }

        String newFrequency = updates.get("frequencia");
        if (newFrequency == null || newFrequency.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("message", "A frequência do hábito não pode ser vazia."));
        }

        habit.setFrequencia(Frequency.valueOf(newFrequency));
        habitsRepository.save(habit);

        return ResponseEntity.ok(Map.of("message", "Frequência do hábito atualizada com sucesso."));
    }

    @PatchMapping("/updatePeriod/{id}")
    public ResponseEntity<?> updateHabitPeriod(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody Map<String, String> updates) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);
        UserDetails userDetails = userRepository.findByEmail(email);

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Habits habit = habitsRepository.findById(id).orElse(null);
        if (habit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Hábito não encontrado."));
        }

        if (!habit.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Você não tem permissão para atualizar este hábito."));
        }

        String newPeriod = updates.get("periodo");
        if (newPeriod == null || newPeriod.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("message", "O período do hábito não pode ser vazio."));
        }

        habit.setPeriodo(Period.valueOf(newPeriod));
        habitsRepository.save(habit);

        return ResponseEntity.ok(Map.of("message", "Período do hábito atualizado com sucesso."));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAllHabits(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);
        UserDetails userDetails = userRepository.findByEmail(email);

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = (User) userDetails;
        List<Habits> habits = habitsRepository.findByUser(user);

        return ResponseEntity.ok(habits);
    }
}