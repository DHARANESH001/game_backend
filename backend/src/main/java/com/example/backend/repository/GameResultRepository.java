package com.example.backend.repository;

import com.example.backend.entity.GameResult;
import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {
    Optional<GameResult> findByUser(User user);

    // ✅ Fetch leaderboard in descending order of wins
    @Query("SELECT gr FROM GameResult gr ORDER BY gr.wins DESC")
    List<GameResult> findAllOrderByWinsDesc();
}
