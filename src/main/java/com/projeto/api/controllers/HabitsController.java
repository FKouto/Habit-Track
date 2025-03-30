package com.projeto.api.controllers;

import com.projeto.api.domain.habits.HabitDTO;
import com.projeto.api.domain.habits.Habits;
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
    public ResponseEntity<?> createHabit(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid HabitDTO habitDTO) {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);
        UserDetails userDetails = userRepository.findByEmail(email);

        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        User user = (User) userDetails;
        Habits habit = new Habits(null, user, habitDTO.nomeHabito(), habitDTO.frequencia(), LocalDate.now());
        habitsRepository.save(habit);

        return ResponseEntity.ok(habit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHabit(@RequestHeader("Authorization") String authHeader, @PathVariable Long id)
    {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autorizado!");
        }

        habitsRepository.deleteById(id);
        return ResponseEntity.ok("Hábito deletado com sucesso!");
    }
}