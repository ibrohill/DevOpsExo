package sn.dev.parrainageapp.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sn.dev.parrainageapp.entities.Admin;

public class Model {
    private static final Model instance = new Model();

    private ObservableList<Admin> adminList = FXCollections.observableArrayList();

    private Model() {
        // Initialisez le modèle avec les données initiales si nécessaire
    }

    public static Model getInstance() {
        return instance;
    }

    public ObservableList<Admin> getAdminList() {
        return adminList;
    }

    public void addAdmin(Admin admin) {
        adminList.add(admin);
        // Notifiez les observateurs du changement dans les données
    }

    public void updateAdmin(Admin admin) {
        // Mettez à jour l'administrateur dans la liste et notifiez les observateurs
    }

    public void deleteAdmin(Admin admin) {
        adminList.remove(admin);
        // Notifiez les observateurs du changement dans les données
    }

    public void setAdminList(ObservableList<Admin> admin) {
    }

    public void setLoggedInUserRole(UserRole role) {
    }


    public String getLoggedInUserRole() {
        return null;
    }
}
