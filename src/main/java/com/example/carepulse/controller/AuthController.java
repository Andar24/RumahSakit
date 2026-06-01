package com.example.carepulse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static List<Map<String, String>> users = new ArrayList<>();

    public AuthController() {
        // Akun Admin Default
        Map<String, String> admin = new HashMap<>();
        admin.put("email", "admin@klinik.com");
        admin.put("password", "123");
        admin.put("role", "ADMIN");
        admin.put("nama", "Admin Utama");
        users.add(admin);

        // Akun Staf Poli Default (BARU)
        Map<String, String> poli = new HashMap<>();
        poli.put("email", "poli@klinik.com");
        poli.put("password", "123");
        poli.put("role", "POLI");
        poli.put("nama", "Resepsionis Poli Anak");
        users.add(poli);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        for (Map<String, String> user : users) {
            if (user.get("email").equals(data.get("email")) && user.get("password").equals(data.get("password"))) {
                Map<String, Object> res = new HashMap<>();
                res.put("status", "success");
                res.put("role", user.get("role"));
                res.put("nama", user.get("nama"));
                res.put("email", user.get("email"));
                return ResponseEntity.ok(res);
            }
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email atau Sandi salah!"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> data) {
        return createUser(data, "PASIEN");
    }

    @PostMapping("/admin/add-dokter")
    public ResponseEntity<?> addDokter(@RequestBody Map<String, String> data) {
        return createUser(data, "DOKTER");
    }

    @PostMapping("/admin/add-admin")
    public ResponseEntity<?> addAdmin(@RequestBody Map<String, String> data) {
        return createUser(data, "ADMIN");
    }

    // FITUR BARU: ADMIN MENAMBAH STAF POLI
    @PostMapping("/admin/add-poli")
    public ResponseEntity<?> addPoli(@RequestBody Map<String, String> data) {
        return createUser(data, "POLI");
    }

    private ResponseEntity<?> createUser(Map<String, String> data, String role) {
        for (Map<String, String> u : users) {
            if (u.get("email").equals(data.get("email"))) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email sudah terdaftar!"));
            }
        }
        Map<String, String> newUser = new HashMap<>();
        newUser.put("email", data.get("email"));
        newUser.put("password", data.get("password"));
        newUser.put("nama", data.get("nama"));
        newUser.put("role", role);
        users.add(newUser);
        return ResponseEntity.ok(Collections.singletonMap("status", "success"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> data) {
        for (Map<String, String> user : users) {
            if (user.get("email").equals(data.get("email"))) {
                if (user.get("password").equals(data.get("oldPassword"))) {
                    user.put("password", data.get("newPassword"));
                    return ResponseEntity.ok(Collections.singletonMap("status", "success"));
                } else {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Sandi lama salah!"));
                }
            }
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Akses ditolak!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        for (Map<String, String> user : users) {
            if (user.get("email").equals(email)) {
                user.put("password", "123456");
                return ResponseEntity.ok(Collections.singletonMap("message", "Link reset berhasil dikirim! (Simulasi: Sandi direset jadi '123456')"));
            }
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email tidak ditemukan!"));
    }
}