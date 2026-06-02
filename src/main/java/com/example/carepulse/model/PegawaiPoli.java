package com.example.carepulse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pegawai_poli")
public class PegawaiPoli extends User {
    private String shiftKerja;

    // Relasi Pegawai ditugaskan di Poli mana
    @ManyToOne
    @JoinColumn(name = "poli_id")
    private Poliklinik poliklinik;

    public PegawaiPoli() {}

    public PegawaiPoli(String email, String password, String namaLengkap, String shiftKerja, Poliklinik poliklinik) {
        super(email, password, "PEGAWAI_POLI", namaLengkap);
        this.shiftKerja = shiftKerja;
        this.poliklinik = poliklinik;
    }

    // Getters and Setters
    public String getShiftKerja() { return shiftKerja; }
    public void setShiftKerja(String shiftKerja) { this.shiftKerja = shiftKerja; }
    public Poliklinik getPoliklinik() { return poliklinik; }
    public void setPoliklinik(Poliklinik poliklinik) { this.poliklinik = poliklinik; }
}