package com.example.backend.dto;

public class LeaderBoardDTO {
    private String username;
    private int wins;

    public LeaderBoardDTO(String username, int wins) {
        this.username = username;
        this.wins = wins;
    }

    public String getUsername() {
        return username;
    }

    public int getWins() {
        return wins;
    }
}
