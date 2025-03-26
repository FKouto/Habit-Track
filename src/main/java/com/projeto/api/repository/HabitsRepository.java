package com.projeto.api.repository;

import com.projeto.api.model.Habits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitsRepository extends JpaRepository<Habits, Long> {
}
