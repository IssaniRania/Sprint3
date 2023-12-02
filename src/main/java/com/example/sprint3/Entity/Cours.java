package com.example.sprint3.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Document(collection = "cours")
@TypeAlias("")
public class Cours {
    @Id
    private String id;
    private String nom;
    private String description;
    private Date datedebut;
    private Date datefin;

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    private byte[] fichier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    }
}
