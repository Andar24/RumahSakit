package com.example.carepulse.config;

import com.example.carepulse.model.*;
import com.example.carepulse.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner initDatabase(UserRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Dokter("dr.julian@klinik.com", "123", "Dr. Julian", "Penyakit Dalam"));
                repo.save(new Pasien("alex@klinik.com", "123", "Alex Johnson", "O"));
                repo.save(new Admin("sarah@klinik.com", "123", "Sarah Chen", "Operasional"));
                System.out.println("✅ Data Dummy Berjaya Dimasukkan ke Database!");
            }
        };
    }
}