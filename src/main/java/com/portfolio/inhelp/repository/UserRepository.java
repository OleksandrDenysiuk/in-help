package com.portfolio.inhelp.repository;

import com.portfolio.inhelp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
