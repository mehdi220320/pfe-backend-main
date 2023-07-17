package com.fsb.PFE.Artiste;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class FollowedArtistDTO{
    private String name;
    private String lastName;
    private String profileImg;
    private String profileId;
    private String specialite;
    private String telephone;

}
