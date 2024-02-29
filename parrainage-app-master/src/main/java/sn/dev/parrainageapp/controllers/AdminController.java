package sn.dev.parrainageapp.controllers;

import javafx.application.Preloader;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.ToString;
import sn.dev.parrainageapp.DBConnection;
import sn.dev.parrainageapp.entities.Role;
import sn.dev.parrainageapp.entities.Utilisateur;
import sn.dev.parrainageapp.repositories.role.IRole;
import sn.dev.parrainageapp.repositories.role.RoleImpl;
import sn.dev.parrainageapp.tools.Notification;
import java.security.SecureRandom;
import java.util.Random;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class AdminController implements Initializable {

    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-=_+[]{}|;:'\"<>,.?/";

    @FXML
    private TableColumn<Utilisateur, Boolean> activedCol;

    @FXML
    private CheckBox activedtfd;

    @FXML
    private Button desactivebtn;

    @FXML
    private Button enregistrebtn;

    @FXML
    private TableColumn<Utilisateur, Integer> idcCol;

    @FXML
    private TextField logintfd;

    @FXML
    private Button modifierbtn;

    @FXML
    private TableColumn<Utilisateur, Boolean> logincol;

    @FXML
    private TableColumn<Utilisateur, String> nomCol;

    @FXML
    private TextField nomtfd;

    @FXML
    private TableColumn<Utilisateur, String> prenomCol;

    @FXML
    private TextField prnomtfd;

    @FXML
    private TableColumn<Utilisateur, String> roleCol;

    @FXML
    private Button suprimerbtn;

    @FXML
    private TableView<Utilisateur> usertd;
    @FXML
    private ComboBox<Role> rolecbb;
    private DBConnection db = new DBConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        loadcbb();
    }


    public ObservableList<Role> getRole() {
        ObservableList<Role> role = FXCollections.observableArrayList();
        String sql = "SELECT * FROM ROLE ";
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {

                role.add(new Role(rs.getInt("id"), rs.getString("name"), rs.getInt("etat")));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return role;

    }

    public ObservableList<Utilisateur> getUtilisateur() {
        ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM USER ORDER BY NOM ASC ";
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setLogin(rs.getString("login"));

                IRole iRole = new RoleImpl();
                Role profil = iRole.getRoleById(rs.getInt("profil"));
                user.setProfil(profil);
                utilisateurs.add(user);
            }
            db.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return utilisateurs;
    }


    public void loadcbb() {
        rolecbb.getItems().addAll(getRole());
    }

    @FXML
    void enregistrer(ActionEvent event) {
        try {
            // Créez un objet Utilisateur avec les valeurs des champs

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(nomtfd.getText());
            utilisateur.setPrenom(prnomtfd.getText());
            utilisateur.setLogin(logintfd.getText());



            // Utilisez l'interface IRole pour obtenir l'ID du rôle par son nom
            IRole iRole = new RoleImpl();
            Role profil = rolecbb.getValue();

            // Affectez le rôle à l'utilisateur
            utilisateur.setProfil(profil);

            // Insérez l'utilisateur dans la base de données
            int ok = insererUtilisateur(utilisateur);

            if (ok > 0) {
                // Si l'insertion est réussie, mettez à jour la table et réinitialisez les champs
                loadTable();
                clearFields();
                Notification.NotifSuccess("Succès", "Candidat ajouté");
            } else {
                Notification.NotifError("Erreur", "Échec de l'ajout du candidat");
            }
        } catch (SQLException e) {
            // Gérez les erreurs SQL
            e.printStackTrace();
            Notification.NotifError("Erreur", "Erreur lors de l'ajout du candidat");
        }
    }

    // Méthode pour insérer un utilisateur dans la base de données
    private int insererUtilisateur(Utilisateur utilisateur) throws SQLException {
        String sql = "INSERT INTO user (nom, prenom, login, password, actived, profil) VALUES (?, ?, ?, ?, ?,?)";

        db.initPrepar(sql);
        db.getPstm().setString(1, utilisateur.getNom());
        db.getPstm().setString(2, utilisateur.getPrenom());
        db.getPstm().setString(3, utilisateur.getLogin());
        String password = generatePassword(12, true, true, true);
        db.getPstm().setString(4, password);
        db.getPstm().setBoolean(5, activedtfd.isSelected());
        db.getPstm().setInt(6, utilisateur.getProfil().getId());
        return db.executeMaj();
    }


    @FXML
    void clear(ActionEvent event) {
        clearFields();
    }

    void clearFields() {
        nomtfd.setText("");
        logintfd.setText("");
        prnomtfd.setText("");

    }


    public void loadTable() {
        ObservableList<Utilisateur> liste = getUtilisateur();
        usertd.setItems(liste);
        idcCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        activedCol.setCellValueFactory(new PropertyValueFactory<>("actived"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("profil"));
        logincol.setCellValueFactory(new PropertyValueFactory<>("login"));


    }


    @FXML
    void desactiver(ActionEvent event) {

    }


    @FXML
    void check(ActionEvent event) {

    }



    @FXML
    void modifier(ActionEvent event) {
        try {
            // Créez un objet Utilisateur avec les valeurs des champs
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(nomtfd.getText());
            utilisateur.setPrenom(prnomtfd.getText());
            utilisateur.setLogin(logintfd.getText());

            // Utilisez l'interface IRole pour obtenir l'ID du rôle par son nom
            IRole iRole = new RoleImpl();
            Role profil = rolecbb.getValue();

            // Affectez le rôle à l'utilisateur
            utilisateur.setProfil(profil);

            // Insérez l'utilisateur dans la base de données
            int ok = updateUtilisateur(utilisateur);

            if (ok > 0) {
                // Si l'insertion est réussie, mettez à jour la table et réinitialisez les champs
                loadTable();
                clearFields();
                Notification.NotifSuccess("Succès", "Candidat ajouté");
            } else {
                Notification.NotifError("Erreur", "Échec de l'ajout du candidat");
            }
            enregistrebtn.setDisable(false);
            loadTable();
            clearFields();
        } catch (SQLException e) {
            // Gérez les erreurs SQL
            e.printStackTrace();
            Notification.NotifError("Erreur", "Erreur lors de modification  du candidat");
        }


    }

    private int updateUtilisateur(Utilisateur utilisateur) throws SQLException {
        String sql = "UPDATE user SET nom = ?, prenom = ?, password = ?, actived = ?, profil = ? WHERE id = ?";
        db.initPrepar(sql);
        db.getPstm().setString(1, utilisateur.getNom());
        db.getPstm().setString(2, utilisateur.getPrenom());

        String password = generatePassword(12, true, true, true);
        db.getPstm().setString(3, password);
        db.getPstm().setBoolean(4, activedtfd.isSelected());
        db.getPstm().setInt(5, utilisateur.getProfil().getId());
        db.getPstm().setInt(6, getIdUtilisateurSelectionne());
        return db.executeMaj();

    }
    // Méthode pour récupérer l'ID de l'utilisateur sélectionné dans la TableView
    private int getIdUtilisateurSelectionne() {
        // Récupérez la ligne sélectionnée dans la TableView
        Utilisateur utilisateurSelectionne = usertd.getSelectionModel().getSelectedItem();

        // Vérifiez si une ligne est effectivement sélectionnée
        if (utilisateurSelectionne != null) {
            // Retournez l'ID de l'utilisateur sélectionné
            return utilisateurSelectionne.getId();
        } else {
            // Aucune ligne sélectionnée, retournez une valeur par défaut (vous pouvez définir votre propre logique ici)
            return -1;
        }
    }


    @FXML
    void suprimer(ActionEvent event) {
        // Récupérez l'ID de l'utilisateur que vous souhaitez supprimer
        int idUtilisateur = getIdUtilisateurSelectionne();

        if (idUtilisateur > 0) {
            String sql = "DELETE FROM user WHERE id = ?";

            try {
                db.initPrepar(sql);
                db.getPstm().setInt(1, idUtilisateur);
                int ok = db.executeMaj();
                db.closeConnection();

                if (ok > 0) {
                    loadTable(); // Rechargez la table après la suppression
                    clearFields();
                    Notification.NotifSuccess("Succès", "Utilisateur supprimé");
                } else {
                    Notification.NotifError("Erreur", "Échec de la suppression de l'utilisateur");
                }
            } catch (SQLException e) {
                // Gérez les erreurs SQL
                e.printStackTrace();
                Notification.NotifError("Erreur", "Erreur lors de la suppression de l'utilisateur");
            }
        } else {
            Notification.NotifError("Erreur", "Veuillez sélectionner un utilisateur à supprimer");
        }
    }


    @FXML
    void getData(MouseEvent event) {
        Utilisateur admi = usertd.getSelectionModel().getSelectedItem();
        int idamin = admi.getId();
        if (admi != null) {
            idamin = admi.getId();
        } else {
            // Gérer le cas où admi est null
            Notification.NotifError("Erreur", "veuillez selectionner un champs");
        }
        nomtfd.setText(admi.getNom());
        prnomtfd.setText(admi.getPrenom());
        activedCol.setText(admi.getLogin());
        enregistrebtn.setDisable(true);

    }



    public static String generatePassword(int length, boolean includeUppercase, boolean includeDigits, boolean includeSpecialChars) {
        StringBuilder password = new StringBuilder();
        String allCharacters = LOWERCASE_CHARACTERS;

        if (includeUppercase) {
            allCharacters += UPPERCASE_CHARACTERS;
        }
        if (includeDigits) {
            allCharacters += DIGITS;
        }
        if (includeSpecialChars) {
            allCharacters += SPECIAL_CHARACTERS;
        }

        Random random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allCharacters.length());
            char randomChar = allCharacters.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }


}

