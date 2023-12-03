package com.example.sprint3.Controller;

import com.example.sprint3.DAO.CoursRepository;
import com.example.sprint3.Entity.Cours;
import com.example.sprint3.Service.CoursService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cours")
public class CoursController {
    private final CoursRepository coursRepository;
    CoursService coursService;

    @Autowired
    public CoursController(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    // get
    @GetMapping
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    // get selon id
    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable String id) {
        Optional<Cours> cours = coursRepository.findById(id);
        return cours.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    //post ajouts d'un cours
    @PostMapping("/ajouterCours")
    public ResponseEntity<String> ajouterCours(
            @RequestParam("datedebut") Date datedebut,
            @RequestParam("datefin") Date datefin,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam(name = "fichier", required = false) MultipartFile fichier
    ) {
        try {
            Cours cours = new Cours();
            cours.setNom(nom);
            cours.setDescription(description);
            cours.setDatedebut(datedebut);
            cours.setDatefin(datefin);
            if (fichier != null && !fichier.isEmpty()) {
                byte[] fichierBytes = fichier.getBytes();
                // Your code to process the file bytes goes here
                // For example, save the bytes to the database or perform other operations
                cours.setFichier(fichierBytes);
            }

            coursRepository.save(cours);

            return ResponseEntity.ok("Cours ajouté avec succès !");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'ajout du cours : " + e.getMessage());
        }
    }
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<Cours> replaceCours(
        @PathVariable String id,
        @RequestPart("nom") String nom,
        @RequestPart("description") String description,
        @RequestPart("fichier") MultipartFile fichier,
        @RequestPart("datedebut") Date datedebut,
        @RequestPart("datefin") Date datefin) {

    Optional<Cours> existingCours = coursRepository.findById(id);

    if (existingCours.isPresent()) {
        Cours cours = existingCours.get();

        // Validation (exemple : vérification des champs non nuls)
        if (nom != null) {
            cours.setNom(nom);
        }

        if (description != null) {
            cours.setDescription(description);
        }

        if (datedebut != null) {
            cours.setDatedebut(datedebut);
        }

        if (datefin != null) {
            cours.setDatefin(datefin);
        }


        try {
            if (fichier != null && !fichier.isEmpty()) {
                byte[] fichierBytes = fichier.getBytes();
                cours.setFichier(fichierBytes);
            }

            Cours updatedCours = coursRepository.save(cours);
            return ResponseEntity.ok(updatedCours);
        } catch (Exception e) {
            // Gérer l'exception, par exemple, la journaliser
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    } else {
        return ResponseEntity.notFound().build();
    }
}


    @DeleteMapping("/delete/{id}")
    void deleteCours(@PathVariable String id) {
        coursRepository.deleteById(id);
    }
}

