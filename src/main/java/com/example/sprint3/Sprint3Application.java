package com.example.sprint3;

import com.example.sprint3.DAO.CoursRepository;
import com.example.sprint3.DAO.TravailRepository;
import com.example.sprint3.Entity.Cours;
import com.example.sprint3.Entity.Travail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
@SpringBootApplication
public class Sprint3Application {
    @Autowired
    private TravailRepository travailRepository;
    public static void main(String[] args) {
        SpringApplication.run(Sprint3Application.class, args);
    }
//    @Bean
//    public CommandLineRunner initDatabase() {
//        return args -> {
//            // Code à exécuter au démarrage de l'application
//
//            Travail travail = new Travail();
//            travail.setTitle("Titre du travail");
//            travailRepository.save(travail);
//
//            // Ajouter d'autres entités Travail si nécessaire
//        };
//    }

}
