package com.example.sprint3;

import com.example.sprint3.DAO.CoursRepository;
import com.example.sprint3.Entity.Cours;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
@SpringBootApplication
public class Sprint3Application {

    public static void main(String[] args) {
        SpringApplication.run(Sprint3Application.class, args);
    }
}
