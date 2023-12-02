package com.example.sprint3.Service;

import com.example.sprint3.DAO.CoursRepository;
import com.example.sprint3.Entity.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursService {
    @Autowired
    private CoursRepository coursRepository;

    public void updateCours(String coursId, Cours updatedCours) {
        // Retrieve the existing document
        Cours existingCours = coursRepository.findById(coursId).orElse(null);

        if (existingCours != null) {
            // Update fields with the new values
            existingCours.setNom(updatedCours.getNom());
            existingCours.setDescription(updatedCours.getDescription());
            // Update other fields as needed

            // Save the updated document
            coursRepository.save(existingCours);
        } else {
            // Handle the case when the document is not found
            // You can throw an exception or handle it based on your requirements
        }
    }
}
