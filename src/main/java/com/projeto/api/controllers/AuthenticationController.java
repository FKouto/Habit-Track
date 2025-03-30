package com.projeto.api.controllers;

import com.projeto.api.domain.user.AuthenticationDTO;
import com.projeto.api.domain.user.RegisterDTO;
import com.projeto.api.domain.user.User;
import com.projeto.api.infra.security.TokenService;
import com.projeto.api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity loign(@RequestBody @Valid AuthenticationDTO data) {
        if (data.email().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("message:", "Campos em branco."));
        }

        if (repository.findByEmail(data.email()) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message:", "Credenciais inválidas."));
        }

        if (data.password().length() < 8) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("message:", "A senha deve possuir 8 caracteres ou mais."));
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message:", token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {

        if (data.nome().matches(".*\\d.*")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("message:", "O nome não pode conter números."));
        }

        if (repository.findByEmail(data.email()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message:", "O e-mail informado já está em uso."));
        }

        if (data.senha().length() < 8) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("message:", "A senha deve possuir 8 caracteres ou mais."));
        }

        if (data.nome().isEmpty() || data.email().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("message:", "Campos em branco."));
        }

        String capitalizedNome = capitalizeName(data.nome());
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        User newUser = new User(capitalizedNome, data.email(), encryptedPassword);
        this.repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message:", "Usuario registrado com sucesso!"));
    }

    private String capitalizeName(String name) {
        String[] words = name.split("\\s+");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                           .append(word.substring(1).toLowerCase())
                           .append(" ");
            }
        }
        return capitalized.toString().trim();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String authHeader)
    {
        String token = authHeader.replace("Bearer ", "");
        String email = tokenService.validateToken(token);

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autorizado!");
        }

        UserDetails userDetails = repository.findByEmail(email);

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Não foi possível processar a sua solicitação!");
        }

        User user = (User) userDetails;
        repository.deleteById(user.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message:", "Usuario registrado com sucesso!"));
    }
}
