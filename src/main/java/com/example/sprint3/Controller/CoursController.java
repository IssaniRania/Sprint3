package com.example.sprint3.Controller;

import com.example.sprint3.DAO.CoursRepository;
import com.example.sprint3.Entity.Cours;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cours")
public class CoursController {
    private final CoursRepository coursRepository;

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
        Optional<Cours> cours = coursRepository.findById(new ObjectId(id));
        return cours.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //post ajouts d'un cours
    @PostMapping("/ajouterCours")
    public ResponseEntity<String> ajouterCours(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("fichier") MultipartFile fichier
    ) {
        try {
            Cours cours = new Cours();
            cours.setNom(nom);
            cours.setDescription(description);
            cours.setFichier(fichier.getBytes());
            coursRepository.save(cours);

            return ResponseEntity.ok("Cours ajouté avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'ajout du cours : " + e.getMessage());
        }
    }
    //put
    @PutMapping("/updateCours/{_id}")
    public ResponseEntity<String> updateCours(
            @PathVariable String _id,
            @RequestPart("updatedCours") Cours updatedCours,
            @RequestPart("fichier") MultipartFile fichier
    ) {
        try {
            Optional<Cours> existingCours = coursRepository.findById(new ObjectId(_id));

            if (existingCours.isPresent()) {
                Cours existingCourse = existingCours.get();

                // Set the fields you want to update from updatedCours
                existingCourse.setNom(updatedCours.getNom());
                existingCourse.setDescription(updatedCours.getDescription());

                // Update the fichier only if a new file is provided
                if (!fichier.isEmpty()) {
                    existingCourse.setFichier(fichier.getBytes());
                }

                // Save the updated course
                coursRepository.save(existingCourse);

                return ResponseEntity.ok("Course updated successfully");
            } else {
                return ResponseEntity.status(404).body("Course not found with ID: " + _id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating course: " + e.getMessage());
        }
    }

}
