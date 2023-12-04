package com.crowdfund.demo.repository;

import com.crowdfund.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE LOWER(p.title) = :title")
    Optional<Project>  findProjectBytitle(String title);

    @Query("SELECT p FROM Project p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Project> findByTitleContainingIgnoreCase(String search);

    @Query("SELECT p FROM Project p WHERE (:search is null or LOWER(p.title) LIKE %:search%) AND (:userId is null or p.user.id = :userId)")
    List<Project> findByTitleContainingIgnoreCaseAndUserId(@Param("search") String search, @Param("userId") Long userId);
}
