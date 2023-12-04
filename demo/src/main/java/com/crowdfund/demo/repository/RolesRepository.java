package com.crowdfund.demo.repository;

import com.crowdfund.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByRole(Role role);
}
