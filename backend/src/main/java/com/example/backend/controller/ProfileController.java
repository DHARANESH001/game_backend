package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import com.example.backend.config.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173") // Adjust based on React port
public class ProfileController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public ProfileController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Get User Profile
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token format");
        }

        String email = extractEmailFromToken(token);
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return ResponseEntity.ok(user);
    }

    // ✅ Update Profile (Only personal details)
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestHeader("Authorization") String token,
                                              @RequestBody User updatedUser) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token format");
        }

        String email = extractEmailFromToken(token);
        User existingUser = userService.getUserByEmail(email);
        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // ✅ Do not allow username or email change
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(existingUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update email");
        }

        User savedUser = userService.updateProfile(email, updatedUser);
        return ResponseEntity.ok(savedUser);
    }

    // ✅ Extract email from JWT token
    private String extractEmailFromToken(String token) {
        String jwt = token.substring(7); // Remove "Bearer "
        return jwtUtil.extractEmail(jwt); // Updated method
    }
    @PostMapping("/upload-image")
public ResponseEntity<String> uploadProfileImage(
        @RequestHeader("Authorization") String token,
        @RequestParam("image") MultipartFile file) {
    try {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select an image.");
        }

        String email = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        user.setProfileImage(file.getBytes());
        userService.saveUser(user);

        return ResponseEntity.ok("Image uploaded successfully!");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
    }
}

@GetMapping("/profile-image")
public ResponseEntity<byte[]> getProfileImage(@RequestHeader("Authorization") String token) {
    String email = jwtUtil.extractUsername(token.substring(7));
    User user = userService.getUserByEmail(email);

    if (user == null || user.getProfileImage() == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok()
            .header("Content-Type", "image/jpeg") // Adjust type if needed
            .body(user.getProfileImage());
}

}
