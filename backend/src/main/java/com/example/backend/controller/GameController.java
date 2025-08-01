package com.example.backend.controller;

import com.example.backend.dto.LeaderBoardDTO;
import com.example.backend.entity.GameResult;
import com.example.backend.entity.User;
import com.example.backend.service.GameResultService;
import com.example.backend.service.UserService;
import com.example.backend.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:5173")
public class GameController {

    private final GameResultService gameResultService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public GameController(GameResultService gameResultService, UserService userService, JwtUtil jwtUtil) {
        this.gameResultService = gameResultService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Record Win when X wins
    @PostMapping("/win")
    public ResponseEntity<GameResult> recordWin(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String email = jwtUtil.extractUsername(jwt);

        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        GameResult result = gameResultService.recordWin(user);
        return ResponseEntity.ok(result);
    }

    // ✅ Fetch Leaderboard (Sorted by wins)
    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderBoardDTO>> getLeaderBoard() {
        List<LeaderBoardDTO> leaderboard = gameResultService.getLeaderBoard();
        return ResponseEntity.ok(leaderboard);
    }
}
