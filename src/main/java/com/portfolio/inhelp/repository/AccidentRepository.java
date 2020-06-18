package com.portfolio.inhelp.repository;

import com.portfolio.inhelp.model.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentRepository extends JpaRepository<Accident, Long> {
}
