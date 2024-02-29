package sn.dev.parrainageapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import sn.dev.parrainageapp.entities.Admin;
import sn.dev.parrainageapp.entities.Electeur;

import java.net.URL;
import java.util.ResourceBundle;

public class ElecteurController implements Initializable {


    @FXML
    private TableView<Electeur> electeurTableView;


    @FXML
    private TableColumn<Electeur, Integer> idCol;

    @FXML
    private TableColumn<Electeur, String> nomCol;

    @FXML
    private TableColumn<Electeur, String> prenomCol;

    @FXML
    private TableColumn<Electeur, String> partieCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (electeurTableView != null) {
            loadtable();
        }

    }



    private ObservableList<Electeur> getElecteurs() {
        ObservableList<Admin> adminList = sn.dev.parrainageapp.entities.Model.getInstance().getAdminList();
        ObservableList<Electeur> electeurList = FXCollections.observableArrayList();
        if (adminList != null && !adminList.isEmpty()) {
            for (Admin admin : adminList) {
                // Pour chaque administrateur, créez un objet Electeur et ajoutez-le à la liste d'électeurs
                Electeur electeur = new Electeur();
                electeur.setId(admin.getId());
                electeur.setNom(admin.getNom());
                electeur.setPrenom(admin.getPrenom());
                electeur.setPartie(admin.getPartie());

                electeurList.add(electeur);
                loadtable();
            }
        } else {
            System.out.println("La liste d'administrateurs est vide .");
        }

        return electeurList;
    }


    private void loadtable() {
        ObservableList<Electeur> liste = getElecteurs();
        electeurTableView.setItems(liste);
        idCol.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        nomCol.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
        prenomCol.setCellValueFactory(cellData -> cellData.getValue().getPrenomProperty());
        partieCol.setCellValueFactory(cellData -> cellData.getValue().getPartieProperty());
    }

    public void getData(MouseEvent mouseEvent) {
    }
}
