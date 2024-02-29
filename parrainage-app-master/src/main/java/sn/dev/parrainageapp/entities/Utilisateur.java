package sn.dev.parrainageapp.entities;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
public class Utilisateur {
    private int id;
    private String nom, prenom, login, password;
    private boolean actived;
    private Role profil;

    public Utilisateur() {

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }


    public void getProfil(int profil) {
    }
}
