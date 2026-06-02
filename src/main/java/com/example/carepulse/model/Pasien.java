package com.example.carepulse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pasiens")
public class Pasien extends User {

    @Column(unique = true, length = 16)
    private String nik;

    private LocalDate tanggalLahir;
    private String jenisKelamin; // "L" atau "P"
    private String golonganDarah;

    // Relasi untuk Akun Keluarga (Orang Tua ke Anak)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "akun_utama_id")
    @JsonIgnore // Mencegah error looping saat sistem membaca data ke JSON
    private Pasien akunUtama;

    @OneToMany(mappedBy = "akunUtama", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pasien> anggotaKeluarga;

    public Pasien() {}

    // --- CONSTRUCTOR LAMA (Memperbaiki error di AuthController & DataSeeder) ---
    public Pasien(String email, String password, String namaLengkap, String golonganDarah) {
        super(email, password, "PASIEN", namaLengkap);
        this.golonganDarah = golonganDarah;
    }

    // --- CONSTRUCTOR BARU (Untuk pendaftaran akun utama/orang tua ke depan) ---
    public Pasien(String email, String password, String namaLengkap, String nik, LocalDate tanggalLahir, String jenisKelamin, String golonganDarah) {
        super(email, password, "PASIEN", namaLengkap);
        this.nik = nik;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.golonganDarah = golonganDarah;
    }

    // --- CONSTRUCTOR KHUSUS (Untuk pendaftaran akun anak) ---
    public Pasien(String namaLengkap, String nik, LocalDate tanggalLahir, String jenisKelamin, String golonganDarah, Pasien akunUtama) {
        // Membuat email sistem otomatis menggunakan NIK agar tidak bentrok
        super(nik + "@keluarga.carepulse.system", "NO_LOGIN", "PASIEN", namaLengkap);
        this.nik = nik;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.golonganDarah = golonganDarah;
        this.akunUtama = akunUtama;
    }

    // --- Getters & Setters ---
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getGolonganDarah() { return golonganDarah; }
    public void setGolonganDarah(String golonganDarah) { this.golonganDarah = golonganDarah; }

    public Pasien getAkunUtama() { return akunUtama; }
    public void setAkunUtama(Pasien akunUtama) { this.akunUtama = akunUtama; }

    public List<Pasien> getAnggotaKeluarga() { return anggotaKeluarga; }

    // Typo 3 'g' sudah diperbaiki menjadi 2 'g' di bawah ini:
    public void setAnggotaKeluarga(List<Pasien> anggotaKeluarga) { this.anggotaKeluarga = anggotaKeluarga; }
}