package com.example.carepulse.controller;

import com.example.carepulse.model.*;
import com.example.carepulse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // --- 1. API Tambah Dokter ---
    @PostMapping("/add-dokter")
    public ResponseEntity<?> addDokter(@RequestBody Map<String, String> data) {
        if (userRepository.existsByEmail(data.get("email"))) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email sudah terdaftar!"));
        }

        // Kita set "Umum" sebagai spesialisasi default jika belum dipilih dari UI
        Dokter dokter = new Dokter(data.get("email"), data.get("password"), data.get("nama"), "Umum");
        userRepository.save(dokter);

        return ResponseEntity.ok(Collections.singletonMap("message", "Akun Dokter berhasil ditambahkan ke Database!"));
    }

    // --- 2. API Tambah Admin ---
    @PostMapping("/add-admin")
    public ResponseEntity<?> addAdmin(@RequestBody Map<String, String> data) {
        if (userRepository.existsByEmail(data.get("email"))) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email sudah terdaftar!"));
        }

        Admin admin = new Admin(data.get("email"), data.get("password"), data.get("nama"), "Manajemen Pusat");
        userRepository.save(admin);

        return ResponseEntity.ok(Collections.singletonMap("message", "Akun Admin berhasil ditambahkan ke Database!"));
    }

    // --- 3. API Tambah Staf Poli ---
    // Pastikan Anda sudah membuat class PegawaiPoli.java pada Fase 1 sebelumnya.
    @PostMapping("/add-poli")
    public ResponseEntity<?> addPoli(@RequestBody Map<String, String> data) {
        if (userRepository.existsByEmail(data.get("email"))) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email sudah terdaftar!"));
        }

        // Objek Poliklinik diset null (kosong) dahulu. Akan dihubungkan nanti.
        PegawaiPoli poli = new PegawaiPoli(data.get("email"), data.get("password"), data.get("nama"), "Shift Reguler", null);
        userRepository.save(poli);

        return ResponseEntity.ok(Collections.singletonMap("message", "Akun Staf Poli berhasil ditambahkan ke Database!"));
    }
}