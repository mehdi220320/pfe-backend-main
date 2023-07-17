package com.fsb.PFE.UtilisateurSimple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class FollowedSimpleUserDTO{
    private String name;
    private String lastName;
    private String profileImg;
    private String profileId;
    private String userName;
}
