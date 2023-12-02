package com.example.sprint3.Controller;

import com.example.sprint3.DAO.CoursRepository;
import com.example.sprint3.Entity.Cours;
import com.example.sprint3.Service.CoursService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateCours(@PathVariable String id, @RequestBody Cours updatedCours) {
//        coursService.updateCours(id, updatedCours);
//        return ResponseEntity.ok("Cours updated successfully");
//    }
    //put
//    @PutMapping("/updateCours/{_id}")
//    public ResponseEntity<String> updateCours(
//            @PathVariable String _id,
//            @RequestPart("updatedCours") Cours updatedCours,
//            @RequestPart("fichier") MultipartFile fichier
//    ) {
//        System.out.println("Attempting to update course with ID: " + _id);
//
//        try {
//            Optional<Cours> existingCours = coursRepository.findById(new ObjectId(_id));
//
//            if (existingCours.isPresent()) {
//                Cours existingCourse = existingCours.get();
//
//                // Set the fields you want to update from updatedCours
//                existingCourse.setNom(updatedCours.getNom());
//                existingCourse.setDescription(updatedCours.getDescription());
//
//                // Update the fichier only if a new file is provided
//                if (!fichier.isEmpty()) {
//                    existingCourse.setFichier(fichier.getBytes());
//                }
//
//                // Save the updated course
//                coursRepository.save(existingCourse);
//
//                return ResponseEntity.ok("Course updated successfully");
//            } else {
//                return ResponseEntity.status(404).body("Course not found with ID: " + _id);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error updating course: " + e.getMessage());
//        }
//    }

}
