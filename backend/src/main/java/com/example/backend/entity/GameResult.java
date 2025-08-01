package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "game_results")
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int wins;

    public GameResult() {}

    public GameResult(User user, String username, int wins) {
        this.user = user;
        this.username = username;
        this.wins = wins;
    }

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getWins() { return wins; }
    public void setWins(int wins) { this.wins = wins; }
}
