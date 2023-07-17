package com.fsb.PFE.UtilisateurSimple;



import com.fsb.PFE.Artiste.Artiste;
import com.fsb.PFE.authentication.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@Data
@ToString
public class UtilisateurSimple extends User {

}
