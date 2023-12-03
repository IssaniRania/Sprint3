package com.example.sprint3.Controller;

import com.example.sprint3.DAO.CoursRepository;
import com.example.sprint3.DAO.TravailRepository;
import com.example.sprint3.Entity.Cours;
import com.example.sprint3.Entity.Travail;
import com.example.sprint3.Service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/travails")
public class TravailController {
    private final TravailRepository travailRepository;

    @Autowired
    public TravailController(TravailRepository travailRepository) {
        this.travailRepository = travailRepository;
    }

    // get
    @GetMapping
    public List<Travail> getAlltravails() {
        return travailRepository.findAll();
    }

    // get selon id
    @GetMapping("/{id}")
    public ResponseEntity<Travail> getTravailById(@PathVariable String id) {
        Optional<Travail> travail= travailRepository.findById(id);
        return travail.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
//post
    @PostMapping("/ajouterTravail")
    public ResponseEntity<String> ajouterCours(
            @RequestParam("title") String title,
            @RequestParam(name = "fichier", required = false) MultipartFile fichier
    ) {
        try {
                Travail travails = new Travail();
                travails.setTitle(title);
            if (fichier != null && !fichier.isEmpty()) {
                byte[] fichierBytes = fichier.getBytes();
                travails.setFichier(fichierBytes);
            }
            travailRepository.save(travails);

            return ResponseEntity.ok("travail ajouté avec succès !");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'ajout du cours : " + e.getMessage());
        }
    }
    //put
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Travail> replaceTravail(
            @PathVariable String id,
            @RequestPart("title") String title,
            @RequestPart("fichier") MultipartFile fichier) {

        Optional<Travail> existingTravails = travailRepository.findById(id);

        if (existingTravails.isPresent()) {
            Travail travail = existingTravails.get();
            travail.setTitle(title); // Utilise le paramètre title plutôt que getTitle()

            if (fichier != null && !fichier.isEmpty()) {
                try {
                    byte[] fichierBytes = fichier.getBytes();
                    travail.setFichier(fichierBytes);
                } catch (IOException e) {
                    // Gérer l'exception, par exemple, la journaliser
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }

            Travail updatedTravails = travailRepository.save(travail);
            return ResponseEntity.ok(updatedTravails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //delete
    @DeleteMapping("/delete/{id}")
    void deleteTravail(@PathVariable String id) {
        travailRepository.deleteById(id);
    }
}
