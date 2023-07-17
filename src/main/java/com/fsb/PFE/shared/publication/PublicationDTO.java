package com.fsb.PFE.shared.publication;

import com.fsb.PFE.authentication.entity.UserDTO;
import com.fsb.PFE.shared.contenue_publication.ContenuePub;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class PublicationDTO {
    private int id;
    private ContenuePub contenu;
    private Date date;
    private UserDTO owner;
}
