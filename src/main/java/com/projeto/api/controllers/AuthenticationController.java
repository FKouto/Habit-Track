package com.projeto.api.controllers;

import com.projeto.api.domain.user.AuthenticationDTO;
import com.projeto.api.domain.user.LoginResponseDTO;
import com.projeto.api.domain.user.RegisterDTO;
import com.projeto.api.domain.user.User;
import com.projeto.api.infra.security.TokenService;
import com.projeto.api.repositories.HabitsRepository;
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
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private HabitsRepository habitsRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        ResponseEntity<LoginResponseDTO> validationResponse = validateLoginData(data);
        if (validationResponse != null) return validationResponse;

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO data) {
        ResponseEntity<String> validationResponse = validateRegisterData(data);
        if (validationResponse != null) return validationResponse;

        String capitalizedNome = capitalizeName(data.nome());
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        User newUser = new User(capitalizedNome, data.email(), encryptedPassword);
        this.repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("Usuario registrado com sucesso!");
    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String authHeader) {
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

        // Deletar todos os hábitos do usuário
        habitsRepository.deleteByUser(user);

        repository.deleteById(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso!");
    }

    private ResponseEntity<LoginResponseDTO> validateLoginData(AuthenticationDTO data) {
        if (data.email().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new LoginResponseDTO("Campos em branco."));
        }

        if (repository.findByEmail(data.email()) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO("Credenciais inválidas."));
        }

        if (data.password().length() < 8) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new LoginResponseDTO("A senha deve possuir 8 caracteres ou mais."));
        }

        return null;
    }

    private ResponseEntity<String> validateRegisterData(RegisterDTO data) {
        if (data.nome().matches(".*\\d.*")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("O nome não pode conter números.");
        }

        if (repository.findByEmail(data.email()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("O e-mail informado já está em uso.");
        }

        if (data.senha().length() < 8) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("A senha deve possuir 8 caracteres ou mais.");
        }

        if (data.nome().isEmpty() || data.email().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Campos em branco.");
        }

        return null;
    }

    private String capitalizeName(String name) {
        String[] words = name.split("\\s+");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                           .append(word.substring(1).toLowerCase())
                           .append(" ");
            }
        }
        return capitalized.toString().trim();
    }
}