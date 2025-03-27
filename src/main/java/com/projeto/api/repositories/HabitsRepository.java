package com.projeto.api.repositories;

import com.projeto.api.domain.habits.Habits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitsRepository extends JpaRepository<Habits, Long> {
}
