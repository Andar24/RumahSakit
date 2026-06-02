package com.example.carepulse.repository;
import com.example.carepulse.model.Poliklinik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliklinikRepository extends JpaRepository<Poliklinik, Long> {}