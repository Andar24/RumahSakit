package com.example.carepulse.model;
import jakarta.persistence.Entity;

@Entity
public class Admin extends User {
    private String departemen;

    public Admin() {}
    public Admin(String email, String password, String namaLengkap, String departemen) {
        super(email, password, "ADMIN", namaLengkap);
        this.departemen = departemen;
    }
    public String getDepartemen() { return departemen; }
}