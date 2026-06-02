package com.example.carepulse.controller;

import com.example.carepulse.model.*;
import com.example.carepulse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@RestController
@RequestMapping("/api/pasien")
public class PasienController {

    @Autowired private PasienRepository pasienRepository;
    @Autowired private PoliklinikRepository poliklinikRepository;
    @Autowired private JanjiTemuRepository janjiTemuRepository;

    // ==========================================
    // 1. FITUR MANAJEMEN KELUARGA
    // ==========================================

    @PostMapping("/add-keluarga")
    public ResponseEntity<?> tambahAnggotaKeluarga(@RequestBody Map<String, String> data) {
        try {
            // Cari akun orang tua yang sedang login
            Optional<Pasien> parentOpt = pasienRepository.findByEmail(data.get("emailParent"));
            if (parentOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Akun utama tidak ditemukan!"));
            }

            Pasien parent = parentOpt.get();
            LocalDate tglLahir = LocalDate.parse(data.get("tanggalLahir")); // Format: YYYY-MM-DD

            // Buat entitas anak/anggota keluarga
            Pasien anak = new Pasien(
                    data.get("namaLengkap"),
                    data.get("nik"),
                    tglLahir,
                    data.get("jenisKelamin"),
                    data.get("golonganDarah"),
                    parent // Set relasi ke orang tua
            );

            pasienRepository.save(anak);
            return ResponseEntity.ok(Collections.singletonMap("message", "Anggota keluarga berhasil ditambahkan!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Gagal menambah keluarga: " + e.getMessage()));
        }
    }

    @GetMapping("/list-keluarga")
    public ResponseEntity<?> getListKeluarga(@RequestParam String emailParent) {
        Optional<Pasien> parentOpt = pasienRepository.findByEmail(emailParent);
        if (parentOpt.isPresent()) {
            Pasien parent = parentOpt.get();
            List<Pasien> keluarga = pasienRepository.findByAkunUtama(parent);

            // Kita kirimkan data orang tua dan anak-anaknya ke frontend
            List<Map<String, Object>> responseList = new ArrayList<>();

            // Masukkan data orang tua
            responseList.add(Map.of("id", parent.getId(), "nama", parent.getNamaLengkap() + " (Diri Sendiri)", "umur", Period.between(parent.getTanggalLahir(), LocalDate.now()).getYears()));

            // Masukkan data anak
            for (Pasien anak : keluarga) {
                responseList.add(Map.of("id", anak.getId(), "nama", anak.getNamaLengkap() + " (Keluarga)", "umur", Period.between(anak.getTanggalLahir(), LocalDate.now()).getYears()));
            }
            return ResponseEntity.ok(responseList);
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Gagal memuat profil."));
    }

    // ==========================================
    // 2. FITUR BOOKING & VALIDASI JANJI TEMU
    // ==========================================

    @PostMapping("/booking")
    public ResponseEntity<?> buatJanjiTemu(@RequestBody Map<String, String> data) {
        try {
            // 1. Cari Pasien yang Berobat (Bisa Orang Tua atau Anak) berdasarkan ID
            Long pasienId = Long.parseLong(data.get("pasienId"));
            Optional<Pasien> pasienOpt = pasienRepository.findById(pasienId);

            if (pasienOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Data pasien tidak valid!"));
            }
            Pasien pasienYangBerobat = pasienOpt.get();

            // 2. Cari Poli yang Dituju
            Long poliId = Long.parseLong(data.get("poliId"));
            Poliklinik poliTarget = poliklinikRepository.findById(poliId).orElseThrow(() -> new Exception("Poli tidak ditemukan"));

            // 3. VALIDASI UMUR (Contoh: Poli Anak maksimal 18 Tahun)
            if (pasienYangBerobat.getTanggalLahir() != null) {
                int umur = Period.between(pasienYangBerobat.getTanggalLahir(), LocalDate.now()).getYears();
                if (poliTarget.getNamaPoli().toLowerCase().contains("anak") && umur > 18) {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message",
                            "DITOLAK: " + pasienYangBerobat.getNamaLengkap() + " berusia " + umur + " tahun. Poli Anak hanya untuk usia 0-18 tahun."));
                }
            }

            // 4. SIMULASI PEMBUATAN TIKET (Ke depannya disambungkan ke Dokter & JadwalPraktik)
            String keluhan = data.get("keluhan");
            String kodeTiket = "CP-" + (System.currentTimeMillis() % 10000);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Tiket berhasil dibuat untuk " + pasienYangBerobat.getNamaLengkap());
            response.put("kodeTiket", kodeTiket);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Terjadi kesalahan: " + e.getMessage()));
        }
    }
}