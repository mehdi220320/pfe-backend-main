package com.fsb.PFE.Artiste;

import com.fsb.PFE.authentication.entity.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Artiste extends User {
    private String specialite;
    private String description;
    private Integer cin;
    private String id_abonnement;
    private String telephone;
}
