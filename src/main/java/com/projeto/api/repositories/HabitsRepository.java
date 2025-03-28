package com.projeto.api.repositories;

import com.projeto.api.domain.habits.Habits;
import com.projeto.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitsRepository extends JpaRepository<Habits, Long> {
    List<Habits> findByUser(User user);
}
