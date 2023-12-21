package com.example.sprint3.Service;

import com.example.sprint3.DAO.CoursRepository;
import com.example.sprint3.Entity.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursService {

    @Autowired
    private CoursRepository coursRepository;

    public void updateCoursByName(String nom, Cours updatedCours) {
        // Chercher l'entité par le nom
        Cours existingCours = coursRepository.findByNom(nom);

        if (existingCours != null) {
            // Mettre à jour les champs avec les nouvelles valeurs
            existingCours.setDescription(updatedCours.getDescription());
            // Mettre à jour d'autres champs au besoin

            // Sauvegarder l'entité mise à jour
            coursRepository.save(existingCours);
        } else {
            // Gérer le cas où l'entité n'est pas trouvée
        }
    }
    public void deleteCoursByNom(String nom){
        coursRepository.deleteByNom(nom);
    }


}
