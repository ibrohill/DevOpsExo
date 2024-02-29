package sn.dev.parrainageapp.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Role {
    private int id;
    private String name;
    private int etat;

    @Override
    public String toString() {
        return name;
    }

}
