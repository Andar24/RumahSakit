package com.example.carepulse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String role;
    private String namaLengkap;

    public User() {}

    public User(String email, String password, String role, String namaLengkap) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.namaLengkap = namaLengkap;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getNamaLengkap() { return namaLengkap; }
}