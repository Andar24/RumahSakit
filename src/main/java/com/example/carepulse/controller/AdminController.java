package com.example.carepulse.controller;

import com.example.carepulse.model.Admin;
import com.example.carepulse.model.Dokter;
import com.example.carepulse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // --- API 1: ADMIN MENAMBAHKAN DOKTER BARU ---
    @PostMapping("/create-dokter")
    public ResponseEntity<?> createDokter(@RequestBody Map<String, String> data) {
        Map<String, Object> response = new HashMap<>();

        if (userRepository.existsByEmail(data.get("email"))) {
            response.put("status", "error");
            response.put("message", "Email sudah terdaftar di sistem!");
            return ResponseEntity.badRequest().body(response);
        }

        Dokter dokterBaru = new Dokter(
                data.get("email"),
                data.get("password"),
                data.get("namaLengkap"),
                data.get("spesialisasi")
        );
        userRepository.save(dokterBaru);

        response.put("status", "success");
        response.put("message", "Akun Dokter berhasil ditambahkan!");
        return ResponseEntity.ok(response);
    }

    // --- API 2: ADMIN MENAMBAHKAN ADMIN BARU ---
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody Map<String, String> data) {
        Map<String, Object> response = new HashMap<>();

        if (userRepository.existsByEmail(data.get("email"))) {
            response.put("status", "error");
            response.put("message", "Email sudah terdaftar di sistem!");
            return ResponseEntity.badRequest().body(response);
        }

        Admin adminBaru = new Admin(
                data.get("email"),
                data.get("password"),
                data.get("namaLengkap"),
                data.get("departemen")
        );
        userRepository.save(adminBaru);

        response.put("status", "success");
        response.put("message", "Akun Admin baru berhasil dibuat!");
        return ResponseEntity.ok(response);
    }
}