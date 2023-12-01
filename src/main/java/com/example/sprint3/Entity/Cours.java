package com.example.sprint3.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Document(collection = "cours")
@TypeAlias("")
public class Cours {
    @Id
    private ObjectId _id;
    private String nom;
    private String description;
    private  byte[] fichier;
    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    @Projection(name = "fullCours",types = Cours.class)
    interface CoursProjection extends Projection{
        public Long getId();
        public String getNom();
        public String getFichier ();
    }
}
