package com.example.carepulse.model;
import jakarta.persistence.Entity;

@Entity
public class Dokter extends User {
    private String spesialisasi;

    public Dokter() {}
    public Dokter(String email, String password, String namaLengkap, String spesialisasi) {
        super(email, password, "DOKTER", namaLengkap);
        this.spesialisasi = spesialisasi;
    }
    public String getSpesialisasi() { return spesialisasi; }
}