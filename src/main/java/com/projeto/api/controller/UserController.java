package com.projeto.api.controller;

import com.projeto.api.model.Users;
import com.projeto.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // Cadastrar um novo usuário
    @PostMapping
    public Users createUser(@RequestBody Users users) {
        users.setData_criacao(LocalDate.now());
        return userRepository.save(users);
    }
}
