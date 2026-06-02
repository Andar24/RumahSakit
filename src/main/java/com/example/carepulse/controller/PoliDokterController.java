package com.example.carepulse.controller;

import com.example.carepulse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/layanan")
public class PoliDokterController {

    @Autowired private JanjiTemuRepository janjiTemuRepository;

    // API Pegawai Poli Check-In Pasien
    @PostMapping("/check-in")
    public ResponseEntity<?> checkInPasien(@RequestBody Map<String, Long> data) {
        // TODO: Ubah status JanjiTemu dari "MENUNGGU_KEDATANGAN" jadi "TIBA_DI_POLI"
        return ResponseEntity.ok(Collections.singletonMap("message", "Pasien berhasil Check-in!"));
    }

    // API Dokter Selesaikan Pemeriksaan
    @PostMapping("/periksa-selesai")
    public ResponseEntity<?> periksaSelesai(@RequestBody Map<String, Object> data) {
        // TODO: Ubah status JanjiTemu jadi "SELESAI" dan simpan Rekam Medis
        return ResponseEntity.ok(Collections.singletonMap("message", "Pemeriksaan selesai!"));
    }
}