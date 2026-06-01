package com.example.carepulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CarepulseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarepulseApplication.class, args);
    }

    @EventListener({ApplicationReadyEvent.class})
    public void openBrowser() {
        System.out.println("=============================================");
        System.out.println("✅ SERVER BERHASIL MENYALA!");
        System.out.println("🌐 Website berjalan di: http://localhost:8080");
        System.out.println("=============================================");

        try {
            // Memaksa sistem Windows untuk membuka browser bawaan
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://localhost:8080");
        } catch (Exception e) {
            System.out.println("⚠️ Gagal membuka browser otomatis. Sila buka browser secara manual ke http://localhost:8080");
        }
    }
}