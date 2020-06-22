package com.portfolio.inhelp.repository;

import com.portfolio.inhelp.model.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccidentRepository extends JpaRepository<Accident, Long> {
    List<Accident> findAllByUserId(Long userId);
}
