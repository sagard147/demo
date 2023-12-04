package com.crowdfund.demo.repository;

import com.crowdfund.demo.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Query("SELECT d FROM Donation d WHERE d.user.Id = :userId")
    List<Donation> findByUserId(Long userId);

    @Query("SELECT d FROM Donation d WHERE d.project.id = :projectId")
    List<Donation> findByProjectId(Long projectId);
}
