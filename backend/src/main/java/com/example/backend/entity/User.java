package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
@Column
private String firstName;

@Column
private String lastName;

@Column
private String phone;

@Column
private String address;

@Column(name = "profile_image", columnDefinition = "LONGBLOB")
private byte[] profileImage;

}
