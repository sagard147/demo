package com.crowdfund.demo.repository;

import com.crowdfund.demo.model.Donation;
import com.crowdfund.demo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    // Additional methods if needed
    @Query("SELECT ur FROM UserRole ur WHERE ur.user.Id = :userId")
    UserRole findByUserId(Long userId);
}
