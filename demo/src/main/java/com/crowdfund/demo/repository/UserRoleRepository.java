package com.crowdfund.demo.repository;

import com.crowdfund.demo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    // Additional methods if needed
}
