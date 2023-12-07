package com.crowdfund.demo.repository;

import com.crowdfund.demo.model.Project;
import com.crowdfund.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:emailId)")
    Optional<User> findbyEmailId(String emailId);
}
