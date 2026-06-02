package com.example.carepulse.controller;

import com.example.carepulse.model.Pasien;
import com.example.carepulse.model.User;
import com.example.carepulse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // 1. LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        Optional<User> userOpt = userRepository.findByEmail(data.get("email"));

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(data.get("password"))) {
                Map<String, Object> res = new HashMap<>();
                res.put("status", "success");

                // Samakan nama role untuk file app.js
                String role = user.getRole();
                if (role.equals("PEGAWAI_POLI")) role = "POLI";

                res.put("role", role);
                res.put("nama", user.getNamaLengkap());
                res.put("email", user.getEmail());
                return ResponseEntity.ok(res);
            }
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email atau Sandi salah!"));
    }

    // 2. REGISTER PASIEN
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> data) {
        if (userRepository.existsByEmail(data.get("email"))) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email sudah terdaftar!"));
        }
        // Simpan pasien ke database permanen
        Pasien pasien = new Pasien(data.get("email"), data.get("password"), data.get("nama"), "-");
        userRepository.save(pasien);
        return ResponseEntity.ok(Collections.singletonMap("status", "success"));
    }

    // 3. UBAH PASSWORD
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> data) {
        Optional<User> userOpt = userRepository.findByEmail(data.get("email"));
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(data.get("oldPassword"))) {
                // Pastikan model User.java Anda sudah memiliki public void setPassword(String password)
                user.setPassword(data.get("newPassword"));
                userRepository.save(user);
                return ResponseEntity.ok(Collections.singletonMap("status", "success"));
            }
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Kata sandi lama salah atau user tidak ditemukan!"));
    }
}