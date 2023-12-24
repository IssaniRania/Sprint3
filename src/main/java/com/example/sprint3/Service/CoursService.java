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



}
