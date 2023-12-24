package com.example.sprint3.Controller;

import com.example.sprint3.DAO.CoursRepository;
import com.example.sprint3.Entity.Cours;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cours")
public class CoursController {
    private final CoursRepository coursRepository;

    @Autowired
    public CoursController(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }
    @GetMapping("/coursByMatiere/{matiereId}")
    public ResponseEntity<List<Cours>> getCoursByMatiere(@PathVariable String matiereId) {
        List<Cours> cours = coursRepository.findByidMatiere(matiereId);
        if (cours.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cours);
    }
    @GetMapping("/getPdf/{filename}")
    public ResponseEntity<Resource> getPdf(@PathVariable String filename) throws IOException {
        Path file = Paths.get("D:/Angular" + filename); // Remplacez par le chemin réel de votre fichier
        UrlResource resource = new UrlResource(file.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body((Resource) resource);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @PostMapping("/ajouterCours/{idMatiere}")
    public ResponseEntity<String> ajouterCours(
            @RequestParam("datedebut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date datedebut,

            @RequestParam("nom") String nom,
            @PathVariable String idMatiere,
            @RequestParam("description") String description,
            @RequestParam(name = "fichier", required = false) MultipartFile fichier
    ) {
        try {
            if (nom == null || nom.trim().isEmpty() || description == null || description.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le nom et la description sont obligatoires.");
            }

            Cours cours = new Cours();
            cours.setNom(nom);
            cours.setDescription(description);
            cours.setDatedebut(datedebut);
            cours.setIdMatiere(idMatiere);
            if (fichier != null && !fichier.isEmpty()) {
                byte[] fichierBytes = fichier.getBytes();
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
    @PutMapping("/updateCours/{id}")
    public ResponseEntity<Cours> replaceCours(
            @PathVariable String id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam(name = "datedebut", required = false) Date datedebut) {

        try {
            Optional<Cours> existingCours = coursRepository.findById(id);

            if (existingCours.isPresent()) {
                Cours cours = existingCours.get();

                if (nom != null && !nom.isEmpty()) {
                    cours.setNom(nom);
                }

                if (description != null && !description.isEmpty()) {
                    cours.setDescription(description);
                }

                if (datedebut != null) {
                    cours.setDatedebut(datedebut);
                }

                Cours updatedCours = coursRepository.save(cours);
                return ResponseEntity.ok(updatedCours);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/delete/{id}")
    void deleteCours(@PathVariable String id) {
        System.out.println("Tentative de suppression du cours avec ID: " + id);
        try {
            coursRepository.deleteById(id);
            System.out.println("Cours supprimé avec succès: " + id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du cours avec ID: " + id);
            e.printStackTrace();
        }
    }


}

