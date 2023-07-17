package com.fsb.PFE.Artiste;

import com.fsb.PFE.UtilisateurSimple.FollowedSimpleUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Following {
    private Set<FollowedArtistDTO> followedArtistDTOSet;
    private Set<FollowedSimpleUserDTO> followedUtilisateurSimpleDTOSet;
}
