package com.projeto.api.repository;

import com.projeto.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email); // Verificar se o e-mail de cadastro já existe
}
