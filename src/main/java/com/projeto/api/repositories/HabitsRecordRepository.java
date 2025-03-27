package com.projeto.api.repositories;

import com.projeto.api.domain.habits.HabitsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitsRecordRepository extends JpaRepository<HabitsRecord, Long> {
}
