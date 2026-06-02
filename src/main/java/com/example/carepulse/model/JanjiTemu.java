package com.example.carepulse.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "janji_temu")
public class JanjiTemu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String kodeTiket; // Contoh: CP-2041

    @ManyToOne
    @JoinColumn(name = "pasien_id", nullable = false)
    private Pasien pasien;

    @ManyToOne
    @JoinColumn(name = "dokter_id", nullable = false)
    private Dokter dokter;

    @ManyToOne
    @JoinColumn(name = "jadwal_id", nullable = false)
    private JadwalPraktik jadwalPraktik;

    private LocalDate tanggalKunjungan;

    // Status: "MENUNGGU_KEDATANGAN", "TIBA_DI_POLI", "SEDANG_DIPERIKSA", "SELESAI", "BATAL"
    private String statusAntrean;

    private String kategori; // "Konsultasi Rutin", "Darurat", dll

    // Tambahkan di dalam file JanjiTemu.java
    private String keluhan;
    private Integer nomorAntreanUrut; // Menyimpan angka urutan, misal: 1, 2, 3

    public JanjiTemu() {}

    public JanjiTemu(String kodeTiket, Pasien pasien, Dokter dokter, JadwalPraktik jadwalPraktik, LocalDate tanggalKunjungan, String kategori) {
        this.kodeTiket = kodeTiket;
        this.pasien = pasien;
        this.dokter = dokter;
        this.jadwalPraktik = jadwalPraktik;
        this.tanggalKunjungan = tanggalKunjungan;
        this.kategori = kategori;
        this.statusAntrean = "MENUNGGU_KEDATANGAN"; // Default status
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getKodeTiket() { return kodeTiket; }
    public void setKodeTiket(String kodeTiket) { this.kodeTiket = kodeTiket; }
    public Pasien getPasien() { return pasien; }
    public void setPasien(Pasien pasien) { this.pasien = pasien; }
    public Dokter getDokter() { return dokter; }
    public void setDokter(Dokter dokter) { this.dokter = dokter; }
    public JadwalPraktik getJadwalPraktik() { return jadwalPraktik; }
    public void setJadwalPraktik(JadwalPraktik jadwalPraktik) { this.jadwalPraktik = jadwalPraktik; }
    public LocalDate getTanggalKunjungan() { return tanggalKunjungan; }
    public void setTanggalKunjungan(LocalDate tanggalKunjungan) { this.tanggalKunjungan = tanggalKunjungan; }
    public String getStatusAntrean() { return statusAntrean; }
    public void setStatusAntrean(String statusAntrean) { this.statusAntrean = statusAntrean; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
}