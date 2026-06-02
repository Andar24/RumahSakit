package com.example.carepulse.repository;
import com.example.carepulse.model.JadwalPraktik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JadwalPraktikRepository extends JpaRepository<JadwalPraktik, Long> {}