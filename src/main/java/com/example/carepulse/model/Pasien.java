package com.example.carepulse.model;
import jakarta.persistence.Entity;

@Entity
public class Pasien extends User {
    private String golonganDarah;

    public Pasien() {}
    public Pasien(String email, String password, String namaLengkap, String golonganDarah) {
        super(email, password, "PASIEN", namaLengkap);
        this.golonganDarah = golonganDarah;
    }
    public String getGolonganDarah() { return golonganDarah; }
}