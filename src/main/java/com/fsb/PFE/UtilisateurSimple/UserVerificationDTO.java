package com.fsb.PFE.UtilisateurSimple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UserVerificationDTO {
    private UtilisateurSimple user;
    private String code;
}
