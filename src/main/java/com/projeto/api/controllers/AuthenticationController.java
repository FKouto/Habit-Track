package com.projeto.api.controllers;

import com.projeto.api.domain.user.Users;
import com.projeto.api.repositories.UserRepository;
import com.projeto.api.infra.security.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthenticationController(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
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

    // Login de usuário
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users) {
        Optional<Users> userOptional = userRepository.findByEmail(users.getEmail());
        if (userOptional.isEmpty() || !Objects.equals(users.getSenha(), userOptional.get().getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Credenciais inválidas."));
        }

        String token = tokenService.generateToken(users);

        return ResponseEntity.ok(Map.of("token", token));
    }
}
