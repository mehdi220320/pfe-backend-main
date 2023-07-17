package com.fsb.PFE.shared.publication;

import com.fsb.PFE.authentication.entity.User;
import com.fsb.PFE.shared.contenue_publication.ContenuePub;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne(cascade = CascadeType.REMOVE)
    @NotNull
    private ContenuePub contenu;
    private Date date;
    private String owner;
}
