package sn.dev.parrainageapp.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sn.dev.parrainageapp.DBConnection;
import sn.dev.parrainageapp.entities.Admin;
import sn.dev.parrainageapp.tools.Notification;
import sn.dev.parrainageapp.tools.Outils;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TextField nomtfd;

    @FXML
    private TextField prenomTfd;

    @FXML
    private TextField partieTfd;

    @FXML
    private Button enregistrerBtn;

    @FXML
    private Button modifierBtn;

    @FXML
    private Button supprimerBtn;

    @FXML
    private Button effacerBtn;

    @FXML
    private TableView<Admin> parrainageTb;

    @FXML
    private TableColumn<Admin, Integer> idCol;

    @FXML
    private TableColumn<Admin, String> nomCol;

    @FXML
    private TableColumn<Admin, String> prenomCol;

    @FXML
    private TableColumn<Admin, String> partieCol;

    private int idAdmin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadtable();
        updateElecteurController();
    }
    private void updateElecteurController() {
        sn.dev.parrainageapp.entities.Model.getInstance().setAdminList(getAdmin());
    }

    private DBConnection db = new DBConnection();


    public ObservableList<Admin> getAdmin() {
        ObservableList<Admin> adminList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM admin ORDER BY id ASC";
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setNom(rs.getString("nom"));
                admin.setPrenom(rs.getString("prenom"));
                admin.setPartie(rs.getString("partie"));

                adminList.add(admin);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();  // Gérez l'exception de manière appropriée
        } finally {
            db.closeConnection();  // Fermez la connexion à la base de données dans un bloc finally
        }

        return adminList;
    }
    public void loadtable(){
        ObservableList<Admin> liste = getAdmin();
        System.out.println(liste);
        parrainageTb.setItems(liste);
        idCol.setCellValueFactory(new PropertyValueFactory<Admin, Integer>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Admin, String>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<Admin, String>("prenom"));
        partieCol.setCellValueFactory(new PropertyValueFactory<Admin, String>("partie"));
    }


    @FXML
    void clear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void delete(ActionEvent event) {
        String sql = "DELETE FROM admin WHERE id = ?";
        try{
            db.initPrepar(sql);
            db.getPstm().setInt(1,idAdmin);
            int ok = db.executeMaj();
            db.closeConnection();
            loadtable();
            clearFields();
            enregistrerBtn.setDisable(false);
            Notification.NotifSuccess("succes","Candidat Supprimer");
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @FXML
    void save(ActionEvent event) {
        String sql = "INSERT INTO admin VALUES (null, ?,?,?)";
        try{
            db.initPrepar(sql);
            db.getPstm().setString(1,nomtfd.getText());
            db.getPstm().setString(2,prenomTfd.getText());
            db.getPstm().setString(3,partieTfd.getText());
            int ok = db.executeMaj();
            db.closeConnection();
            loadtable();
            clearFields();
            Notification.NotifSuccess("succes","Candidat Ajouter");
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @FXML
    void update(ActionEvent event) {
        String sql = "UPDATE admin SET  nom = ?, prenom = ?, partie = ? WHERE id = ?";
        try{
            db.initPrepar(sql);
            db.getPstm().setString(1,nomtfd.getText());
            db.getPstm().setString(2,prenomTfd.getText());
            db.getPstm().setString(3,partieTfd.getText());
            db.getPstm().setInt(4,idAdmin);
            int ok = db.executeMaj();
            db.closeConnection();
            loadtable();
            clearFields();
//            enregistrerBtn.setDisable(false);
            Notification.NotifSuccess("succes","Candidat Modifier");
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    void clearFields(){
        nomtfd.setText("");
        prenomTfd.setText("");
        partieTfd.setText("");
    }

    @FXML
    void getData(MouseEvent event) {
        Admin admi = parrainageTb.getSelectionModel().getSelectedItem();
        idAdmin = admi.getId();
        if (admi != null) {
            idAdmin = admi.getId();
        } else {
            // Gérer le cas où admi est null
        }
        nomtfd.setText(admi.getNom());
        prenomTfd.setText(admi.getPrenom());
        partieTfd.setText(admi.getPartie());
        enregistrerBtn.setDisable(true);
    }

    public void statistique(ActionEvent event) throws IOException {
        Outils.load(event, "Statistique", "/pages/statistique.fxml");
    }
    public void gestionCandidat(ActionEvent event) throws IOException {
        Outils.load(event, "Gestion-Candidat", "/pages/admin.fxml");
    }
    private void checkUserRole() {
        String userRole = sn.dev.parrainageapp.entities.Model.getInstance().getLoggedInUserRole();

        // Désactiver/enabled les boutons en fonction du rôle
        if ("ROLE_CANDIDAT".equals(userRole)) {
            enregistrerBtn.setDisable(true);
            modifierBtn.setDisable(true);
            supprimerBtn.setDisable(true);
        } else {
            enregistrerBtn.setDisable(false);
            modifierBtn.setDisable(false);
            supprimerBtn.setDisable(false);
        }
    }
}



