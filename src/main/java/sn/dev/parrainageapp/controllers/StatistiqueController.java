package sn.dev.parrainageapp.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import sn.dev.parrainageapp.tools.Outils;

import java.io.IOException;

public class StatistiqueController {
    public Button statistiquebtn;



        public void statistique(ActionEvent event) throws IOException {
            Outils.load(event, "statistique", "/pages/statistique.fxml");
        }
    public void gestionCandidat(ActionEvent event) throws IOException {
        Outils.load(event, "Gestion-Candidat", "/pages/admin.fxml");
    }


}

