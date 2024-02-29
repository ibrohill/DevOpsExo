package sn.dev.parrainageapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sn.dev.parrainageapp.entities.Utilisateur;
import sn.dev.parrainageapp.repositories.utilisateur.IUtilisateur;
import sn.dev.parrainageapp.repositories.utilisateur.UtilisateurImpl;
import sn.dev.parrainageapp.tools.Notification;
import sn.dev.parrainageapp.tools.Outils;

public class LoginController {

    @FXML
    private TextField loginTfd;

    @FXML
    private PasswordField passwordTfd;

    private IUtilisateur userDao = new UtilisateurImpl();

    @FXML
    void login(ActionEvent event) {
        String login = loginTfd.getText();
        String password = passwordTfd.getText();
        if(login.trim().equals("") || password.trim().equals("")){
            Notification.NotifError("Erreur","Tous les champs sont obligatoires !");
        }else{
            Utilisateur user = userDao.seConnecter(login, password);

                try{
                    if(user != null){
                     //Notification.NotifSuccess("Succés","Connexion réussie !");
                       // Outils.load(event, "Bienvenue admin", "/pages/admin.fxml");
                        switch (user.getProfil().getName()) {
                            case "ADMIN":
                                Outils.load(event, "Bienvenue admin", "/pages/admin.fxml");
                                break;
                            case "CANDIDAT":
                                Outils.load(event, "Bienvenue candidat ", "/pages/candidat.fxml");
                                break;
                            case "ELECTEUR":
                                Outils.load(event, "Bienvenue electeur ", "/pages/electeur.fxml");
                                break;

                        }
                    }else
                        Notification.NotifError("Erreur","Login et/ou password incorrects !");
                }catch (Exception e){
                    e.printStackTrace();
                }

        }
    }

}
