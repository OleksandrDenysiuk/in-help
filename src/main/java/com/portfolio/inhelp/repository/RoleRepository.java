package com.portfolio.inhelp.repository;

import com.portfolio.inhelp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
