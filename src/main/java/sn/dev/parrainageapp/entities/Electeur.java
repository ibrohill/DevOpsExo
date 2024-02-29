package sn.dev.parrainageapp.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Electeur {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty nom = new SimpleStringProperty();
    private final SimpleStringProperty prenom = new SimpleStringProperty();
    private final SimpleStringProperty partie = new SimpleStringProperty();

    // Ajoutez les constructeurs et les méthodes getters et setters

    public SimpleIntegerProperty getIdProperty() {
        return id;
    }

    public SimpleStringProperty getNomProperty() {
        return nom;
    }

    public SimpleStringProperty getPrenomProperty() {
        return prenom;
    }

    public SimpleStringProperty getPartieProperty() {
        return partie;
    }

    public void setId(int id) {
    }

    public void setNom(String nom) {
    }

    public void setPrenom(String prenom) {
    }

    public void setPartie(String partie) {
    }
}
