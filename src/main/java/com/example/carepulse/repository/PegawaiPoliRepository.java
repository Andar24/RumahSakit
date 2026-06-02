package com.example.carepulse.repository;
import com.example.carepulse.model.PegawaiPoli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PegawaiPoliRepository extends JpaRepository<PegawaiPoli, Long> {}