package com.example.carepulse.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "poliklinik")
public class Poliklinik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String namaPoli;

    private String lokasiRuangan;

    // Relasi 1 Poli bisa memiliki banyak Dokter
    @OneToMany(mappedBy = "poliklinik", cascade = CascadeType.ALL)
    private List<Dokter> daftarDokter;

    public Poliklinik() {}

    public Poliklinik(String namaPoli, String lokasiRuangan) {
        this.namaPoli = namaPoli;
        this.lokasiRuangan = lokasiRuangan;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNamaPoli() { return namaPoli; }
    public void setNamaPoli(String namaPoli) { this.namaPoli = namaPoli; }
    public String getLokasiRuangan() { return lokasiRuangan; }
    public void setLokasiRuangan(String lokasiRuangan) { this.lokasiRuangan = lokasiRuangan; }
}