package com.fsb.PFE.shared.contenue_publication.likes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "like_table")
public class Like {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String idOwner;
}
