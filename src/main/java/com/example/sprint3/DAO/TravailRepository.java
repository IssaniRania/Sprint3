package com.example.sprint3.DAO;

import com.example.sprint3.Entity.Cours;
import com.example.sprint3.Entity.Travail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TravailRepository extends MongoRepository<Travail, String> {
    List<Travail> findByidEleve(String idEleve);
}
