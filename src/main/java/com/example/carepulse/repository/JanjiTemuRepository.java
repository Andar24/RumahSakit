package com.example.carepulse.repository;
import com.example.carepulse.model.JanjiTemu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JanjiTemuRepository extends JpaRepository<JanjiTemu, Long> {}