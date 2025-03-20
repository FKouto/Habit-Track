package com.projeto.api.controller;

import com.projeto.api.model.Users;
import com.projeto.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Cadastrar um novo usuário
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Users users) {
        if (userRepository.existsByEmail(users.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "O e-mail informado já está em uso."));
        }
        if (users.getSenha().length() < 8) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("message", "A senha deve possuir 8 caracteres ou mais."));
        }
        if (users.getNome().isEmpty() || users.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("message", "Campos em branco."));
        }

        users.setData_criacao(LocalDate.now());
        Users savedUser = userRepository.save(users);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
