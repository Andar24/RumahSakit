package com.example.carepulse.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "jadwal_praktik")
public class JadwalPraktik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dokter_id", nullable = false)
    private Dokter dokter;

    private String hari; // Contoh: "Senin", "Selasa"
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private Integer kuotaMaksimal;
    private Integer sisaKuota;

    public JadwalPraktik() {}

    public JadwalPraktik(Dokter dokter, String hari, LocalTime jamMulai, LocalTime jamSelesai, Integer kuotaMaksimal) {
        this.dokter = dokter;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.kuotaMaksimal = kuotaMaksimal;
        this.sisaKuota = kuotaMaksimal; // Sisa kuota awal = kuota maksimal
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Dokter getDokter() { return dokter; }
    public void setDokter(Dokter dokter) { this.dokter = dokter; }
    public String getHari() { return hari; }
    public void setHari(String hari) { this.hari = hari; }
    public LocalTime getJamMulai() { return jamMulai; }
    public void setJamMulai(LocalTime jamMulai) { this.jamMulai = jamMulai; }
    public LocalTime getJamSelesai() { return jamSelesai; }
    public void setJamSelesai(LocalTime jamSelesai) { this.jamSelesai = jamSelesai; }
    public Integer getKuotaMaksimal() { return kuotaMaksimal; }
    public void setKuotaMaksimal(Integer kuotaMaksimal) { this.kuotaMaksimal = kuotaMaksimal; }
    public Integer getSisaKuota() { return sisaKuota; }
    public void setSisaKuota(Integer sisaKuota) { this.sisaKuota = sisaKuota; }
}