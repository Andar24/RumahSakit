package com.example.carepulse.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dokters") // Opsional: Penamaan tabel eksplisit
public class Dokter extends User {
    private String spesialisasi;
    private Double rating = 0.0;

    // Relasi ke Poliklinik
    @ManyToOne
    @JoinColumn(name = "poli_id")
    private Poliklinik poliklinik;

    // Relasi ke Jadwal Praktik
    @OneToMany(mappedBy = "dokter", cascade = CascadeType.ALL)
    private List<JadwalPraktik> jadwalPraktikList;

    public Dokter() {}

    // Constructor LAMA (WAJIB ADA agar AdminController & DataSeeder tidak ERROR)
    public Dokter(String email, String password, String namaLengkap, String spesialisasi) {
        super(email, password, "DOKTER", namaLengkap);
        this.spesialisasi = spesialisasi;
    }

    // Constructor BARU (Untuk masa depan jika poli sudah dipilih)
    public Dokter(String email, String password, String namaLengkap, String spesialisasi, Poliklinik poliklinik) {
        super(email, password, "DOKTER", namaLengkap);
        this.spesialisasi = spesialisasi;
        this.poliklinik = poliklinik;
    }

    // Getters and Setters
    public String getSpesialisasi() { return spesialisasi; }
    public void setSpesialisasi(String spesialisasi) { this.spesialisasi = spesialisasi; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Poliklinik getPoliklinik() { return poliklinik; }
    public void setPoliklinik(Poliklinik poliklinik) { this.poliklinik = poliklinik; }
}