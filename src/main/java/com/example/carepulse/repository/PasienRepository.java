package com.example.carepulse.repository;

import com.example.carepulse.model.Pasien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasienRepository extends JpaRepository<Pasien, Long> {
    Optional<Pasien> findByEmail(String email);
    List<Pasien> findByAkunUtama(Pasien akunUtama);
}