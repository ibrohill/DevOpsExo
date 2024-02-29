package sn.dev.parrainageapp.entities;
import sn.dev.parrainageapp.entities.UserRole;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Utilisateur {
    private int id;
    private String nom, prenom, login, password;
    private int actived;
    private Role profil;
    private UserRole role;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
