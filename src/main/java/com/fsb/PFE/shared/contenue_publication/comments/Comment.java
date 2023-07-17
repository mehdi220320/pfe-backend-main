package com.fsb.PFE.shared.contenue_publication.comments;

import com.fsb.PFE.shared.contenue_publication.ContenuePub;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String userId;
    private Date date;
    private String description;
    private String profileName;
    /*@OneToOne
    private ContenuePub contenuePub;*/
}
