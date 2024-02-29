package sn.dev.parrainageapp.entities;

import lombok.*;



public class Admin {
    private int id;
    private String nom,prenom,partie;

    public Admin() {
    }

    public Admin(int id, String nom, String prenom, String partie) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.partie = partie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPartie() {
        return partie;
    }

    public void setPartie(String partie) {
        this.partie = partie;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", partie='" + partie + '\'' +
                '}';
    }
}
