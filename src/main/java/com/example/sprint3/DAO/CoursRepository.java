package com.example.sprint3.DAO;

import com.example.sprint3.Entity.Cours;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoursRepository extends MongoRepository<Cours, String> {
    Cours findByNom(String nom);
    void deleteByNom(String nom);
}
