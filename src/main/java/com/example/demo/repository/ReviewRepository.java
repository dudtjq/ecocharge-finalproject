package com.example.demo.repository;

import com.example.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT COUNT(r) FROM Review r WHERE r.chargeSpot.statId = :statId")
    int countByStatId(String statId);

    @Query("SELECT r FROM Review r WHERE r.chargeSpot.statId = :statId")
    List<Review> findAllByStatId(String statId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.user.userId = :userId")
    int countByUserId(String userId);

    @Query("SELECT r FROM Review r WHERE r.user.userId = :userId")
    List<Review> findAllByUserId(String userId);
}
