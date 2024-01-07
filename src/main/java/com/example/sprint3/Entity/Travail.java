package com.example.sprint3.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Travail")
public class Travail {
    @Id
    private String id;
    private String title;
    private String idEleve;

    public String getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(String idEleve) {
        this.idEleve = idEleve;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private byte[] fichier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }
}
