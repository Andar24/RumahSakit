package com.example.carepulse.repository;

import com.example.carepulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // Fitur baru: Cek apakah email sudah terdaftar
    boolean existsByEmail(String email);
}